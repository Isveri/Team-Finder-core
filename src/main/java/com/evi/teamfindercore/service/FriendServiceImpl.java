package com.evi.teamfindercore.service;

import com.evi.teamfindercore.domain.Friend;
import com.evi.teamfindercore.domain.FriendRequest;
import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.exception.AlreadyFriendException;
import com.evi.teamfindercore.exception.AlreadyInvitedException;
import com.evi.teamfindercore.exception.UserNotFoundException;
import com.evi.teamfindercore.messaging.NotificationMessagingService;
import com.evi.teamfindercore.messaging.model.Notification;
import com.evi.teamfindercore.mapper.FriendMapper;
import com.evi.teamfindercore.mapper.FriendRequestMapper;
import com.evi.teamfindercore.model.FriendDTO;
import com.evi.teamfindercore.model.FriendRequestDTO;
import com.evi.teamfindercore.repository.FriendRepository;
import com.evi.teamfindercore.repository.FriendRequestRepository;
import com.evi.teamfindercore.repository.UserRepository;
import com.evi.teamfindercore.service.feign.ChatServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.evi.teamfindercore.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {


    private final FriendRequestRepository friendRequestRepository;

    private final FriendRequestMapper friendRequestMapper;

    private final FriendMapper friendMapper;

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;

    private final NotificationMessagingService notificationMessagingService;

    private final ChatServiceFeignClient chatServiceFeignClient;

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }

    @Override
    public void sendFriendRequest(Long invitedUserId) {

        User user = getCurrentUser();
        User invitedUser = getUserById(invitedUserId);
        if (!friendRequestRepository.existsBySendingUserIdAndInvitedUserId(user.getId(), invitedUserId)) {
            if (user.equals(invitedUser)) {
                throw new AlreadyFriendException("Cant invite yourself");
            } else if (IsNotOnFriendList(user, invitedUser)) {
                FriendRequest friendRequest = FriendRequest.builder().sendingUser(user).invitedUser(invitedUser).build();
                friendRequestRepository.save(friendRequest);

                notificationMessagingService.sendNotification(Notification.builder()
                        .notificationType(Notification.NotificationType.FRIENDREQUEST)
                        .userId(invitedUserId).build()
                );
            } else {
                throw new AlreadyInvitedException(invitedUser.getUsername() + " already friend");
            }
        } else {
            throw new AlreadyInvitedException(invitedUser.getUsername() + " already invited");
        }
    }

    @Override
    public List<FriendRequestDTO> loadFriendRequests() {
        return friendRequestRepository.findAllByInvitedUserId(getCurrentUser().getId())
                .stream()
                .map(friendRequestMapper::mapFriendRequestToFriendRequestDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptFriendRequest(Long requestId) {

        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow();
        User user = getUserById(getCurrentUser().getId());
        User sendingUser = getUserById(friendRequest.getSendingUser().getId());

        if (IsNotOnFriendList(sendingUser, user) && getCurrentUser().equals(friendRequest.getInvitedUser())) {

            Long chatId = chatServiceFeignClient.createPrivateChat().getBody();

            Friend friend = Friend.builder().chatId(chatId).user(sendingUser).build();
            friendRepository.save(friend);
            user.getFriendList().add(friend);
            friend = Friend.builder().chatId(chatId).user(user).build();
            sendingUser.getFriendList().add(friend);
            friendRepository.save(friend);

            userRepository.saveAll(Arrays.asList(user, sendingUser));

            notificationMessagingService.sendNotification(Notification.builder()
                    .notificationType(Notification.NotificationType.FRIENDREQUEST)
                    .userId(user.getId()).build());

            notificationMessagingService.sendNotification(Notification.builder()
                    .notificationType(Notification.NotificationType.FRIENDREQUEST)
                    .userId(sendingUser.getId()).build());

        } else {
            throw new AlreadyFriendException(sendingUser.getUsername() + " is already your friend");
        }
        friendRequestRepository.delete(friendRequest);
    }

    @Override
    public void declineRequest(Long requestId) {
        friendRequestRepository.delete(friendRequestRepository.findById(requestId).orElseThrow());
    }

    @Override
    public List<FriendDTO> getFriendList() {
        User user = getCurrentUser();
        return user.getFriendList().stream().map(friendMapper::mapFriendToFriendDTO).collect(Collectors.toList());
    }

    private boolean IsNotOnFriendList(User user, User invitedUser) {
        return invitedUser.getFriendList().stream().filter(friend -> user.equals(friend.getUser())).findFirst().orElse(null) == null;
    }
}

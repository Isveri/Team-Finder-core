package com.evi.teamfindercore.service;

import com.evi.teamfindercore.domain.FriendRequest;
import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.domain.UserFriend;
import com.evi.teamfindercore.exception.AlreadyFriendException;
import com.evi.teamfindercore.exception.AlreadyInvitedException;
import com.evi.teamfindercore.exception.UserNotFoundException;
import com.evi.teamfindercore.messaging.service.NotificationMessagingService;
import com.evi.teamfindercore.messaging.model.Notification;
import com.evi.teamfindercore.mapper.FriendMapper;
import com.evi.teamfindercore.mapper.FriendRequestMapper;
import com.evi.teamfindercore.model.FriendDTO;
import com.evi.teamfindercore.model.FriendRequestDTO;
import com.evi.teamfindercore.repository.FriendRequestRepository;
import com.evi.teamfindercore.repository.UserRepository;
import com.evi.teamfindercore.repository.UsersFriendsRepository;
import com.evi.teamfindercore.service.feign.ChatServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.evi.teamfindercore.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {


    private final FriendRequestRepository friendRequestRepository;

    private final FriendRequestMapper friendRequestMapper;

    private final FriendMapper friendMapper;

    private final UserRepository userRepository;

    private final UsersFriendsRepository usersFriendsRepository;

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

    @Transactional
    @Override
    public void acceptFriendRequest(Long requestId) {

        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow();
        User user = getUserById(getCurrentUser().getId());
        User sendingUser = getUserById(friendRequest.getSendingUser().getId());

        if (IsNotOnFriendList(sendingUser, user) && getCurrentUser().equals(friendRequest.getInvitedUser())) {

            List<UserFriend> userFriends = usersFriendsRepository.findDeletedFriends(user.getId(), sendingUser.getId());
            if (!userFriends.isEmpty()) {
                userFriends.forEach(userFriend -> {
                    userFriend.setDeleted(false);
                    usersFriendsRepository.save(userFriend);
                });
            } else {
                Long chatId = chatServiceFeignClient.createPrivateChat().getBody();
                if (Objects.equals(chatId, null)) {
                    throw new RuntimeException("There is problem creating your private chat, try again later");
                }

                UserFriend userFriend = UserFriend.builder().user(sendingUser).friend(user).chatId(chatId).build();
                usersFriendsRepository.save(userFriend);
                UserFriend acceptingUserFriend = UserFriend.builder().user(user).friend(sendingUser).chatId(chatId).build();
                usersFriendsRepository.save(acceptingUserFriend);

            }
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
        return user.getFriendList().stream().map(friendMapper::mapUsersFriendsToFriendDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Long> removeFriend(Long friendId) {

        User user = getUserById(getCurrentUser().getId());
        List<UserFriend> userFriends = usersFriendsRepository.findMutualFriend(friendId, user.getId());

        userFriends.forEach(userFriend -> {
            userFriend.setDeleted(true);
            usersFriendsRepository.save(userFriend);
        });

        notificationMessagingService.sendNotification(Notification.builder()
                .notificationType(Notification.NotificationType.FRIENDREQUEST)
                .userId(userFriends.get(0).getFriend().getId()).build());

        notificationMessagingService.sendNotification(Notification.builder()
                .notificationType(Notification.NotificationType.FRIENDREQUEST)
                .userId(userFriends.get(0).getUser().getId()).build());

        return userFriends.stream().map(UserFriend::getId).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Long> removeAllFriends() {

        User user = getUserById(getCurrentUser().getId());
        List<Long> removedFriends = new ArrayList<>();
        user.getFriendList().forEach(friend -> {
            removedFriends.addAll(this.removeFriend(friend.getUser().getId()));
        });

        return removedFriends;

    }

    @Transactional
    @Override
    public void rollbackFriendsDeletion(List<Long> friendsIds) {

        friendsIds.forEach(id->{
            UserFriend userFriend = usersFriendsRepository.findDeletedById(id);
            userFriend.setDeleted(false);
            usersFriendsRepository.save(userFriend);
        });


    }

    private boolean IsNotOnFriendList(User user, User invitedUser) {
        return invitedUser.getFriendList().stream().filter(friend -> user.equals(friend.getUser())).findFirst().orElse(null) == null;
    }

}

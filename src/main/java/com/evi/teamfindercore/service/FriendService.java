package com.evi.teamfindercore.service;

import com.evi.teamfindercore.model.FriendDTO;
import com.evi.teamfindercore.model.FriendRequestDTO;

import java.util.List;

public interface FriendService {

    public void sendFriendRequest(Long invitedUserId);

    List<FriendRequestDTO> loadFriendRequests();

    void acceptFriendRequest(Long requestId);

    void declineRequest(Long requestId);

    List<FriendDTO> getFriendList();

    List<Long> removeFriend(Long friendId);

    List<Long> removeAllFriends();

    void rollbackFriendsDeletion(List<Long> friendsIds);
}

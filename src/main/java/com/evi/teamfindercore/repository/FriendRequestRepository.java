package com.evi.teamfindercore.repository;

import com.evi.teamfindercore.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {


    List<FriendRequest> findAllByInvitedUserId(Long invitedUserId);

    boolean existsBySendingUserIdAndInvitedUserId(Long sendingUserId, Long invitedUserId);
}

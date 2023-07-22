package com.evi.teamfindercore.repository;

import com.evi.teamfindercore.domain.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersFriendsRepository extends JpaRepository<UserFriend, Long> {

    @Query("SELECT f FROM UserFriend f WHERE (f.friend.id = :friendId AND f.user.id = :userId) OR (f.friend.id = :userId AND f.user.id = :friendId)")
    List<UserFriend> findMutualFriend(Long friendId, Long userId);

    @Query(value = "SELECT * FROM user_friend f WHERE (friend_id = :friendId AND user_id = :userId) OR (friend_id = :userId AND user_id = :friendId) AND deleted=true", nativeQuery = true)
    List<UserFriend> findDeletedFriends(Long friendId, Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_friend f where id=:id and deleted=true")
    UserFriend findDeletedById(Long id);

}

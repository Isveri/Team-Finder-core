package com.evi.teamfindercore.repository;

import com.evi.teamfindercore.domain.Friend;
import com.evi.teamfindercore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findFriendByChatId(Long chatId);

}

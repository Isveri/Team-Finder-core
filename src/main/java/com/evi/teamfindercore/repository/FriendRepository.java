package com.evi.teamfindercore.repository;

import com.evi.teamfindercore.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}

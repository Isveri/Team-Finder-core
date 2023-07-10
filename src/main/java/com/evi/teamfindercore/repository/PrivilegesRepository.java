package com.evi.teamfindercore.repository;

import com.evi.teamfindercore.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrivilegesRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}

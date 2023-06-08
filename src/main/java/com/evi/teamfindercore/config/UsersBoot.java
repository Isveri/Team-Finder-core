package com.evi.teamfindercore.config;

import com.evi.teamfindercore.domain.Role;
import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.domain.UserInfo;
import com.evi.teamfindercore.repository.RoleRepository;
import com.evi.teamfindercore.repository.UserInfoRepository;
import com.evi.teamfindercore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@RequiredArgsConstructor
@Component
public class UsersBoot implements CommandLineRunner {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }


    private void createUsers() {
        Role role = roleRepository.findByName("ROLE_USER");
        Role admin = roleRepository.findByName("ROLE_ADMIN");

        UserInfo userInfo1 = UserInfo.builder()
                .name("Adam")
                .age(21)
                .city("Lublin")
                .phone(551343223)
                .info("I like cakes")
                .build();


        UserInfo userInfo2 = UserInfo.builder()
                .name("Adam")
                .age(21)
                .city("Lublin")
                .phone(551343223)
                .info("I like cakes")
                .build();

        userInfoRepository.saveAll(Arrays.asList(userInfo1, userInfo2));

        User u1 = User.builder()
                .username("Evi")
                .role(admin)
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .enabled(true)
                .userInfo(userInfo1)
                .build();

        User u2 = User.builder()
                .username("User")
                .email("evistifate1@gmail.com")
                .enabled(true)
                .role(role)
                .password(passwordEncoder.encode("Useruser1!"))
                .userInfo(userInfo2)
                .build();

        User u3 = User.builder()
                .username("Arthur")
                .email("evistifate1@gmail.com")
                .enabled(true)
                .role(role)
                .password(passwordEncoder.encode("Useruser1!"))
                .build();

        User u4 = User.builder()
                .username("William")
                .enabled(true)
                .role(role)
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .build();

        User u5 = User.builder()
                .username("Yeager")
                .enabled(true)
                .role(role)
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .accountNonLocked(false)
                .reason("Toxicity, many reports")
                .bannedBy("Evi")
                .build();

        User u6 = User.builder()
                .username("Satoru")
                .email("evistifate1@gmail.com")
                .role(role)
                .password(passwordEncoder.encode("Useruser1!"))
                .accountNonLocked(false)
                .enabled(true)
                .bannedBy("Evi")
                .reason("Toxicity, trolling, not taking serious warning from administration")
                .build();


        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));

    }

}

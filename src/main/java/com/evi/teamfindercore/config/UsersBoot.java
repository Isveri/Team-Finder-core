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

        User u1 = User.builder()
                .username("Evi")
                .email("evistifate1@gmail.com")
                .name("Patryk")
                .age(21)
                .city("Lublin")
                .role(admin)
                .enabled(true)
                .phone(551345345)
                .info("Challanger in every role in League of Legends. Global elite in CS:GO and Immortal in Valorant. 706gs BDO kek")
                .password(passwordEncoder.encode("Useruser1!"))
                .build();

        User u2 = User.builder()
                .username("User")
                .email("evistifate1@gmail.com")
                .name("Adam")
                .enabled(true)
                .role(role)
                .age(21)
                .city("Lublin")
                .phone(551343223)
                .info("I like cakes")
                .password(passwordEncoder.encode("Useruser1!"))
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
                .role(role)
                .enabled(true)
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .build();

        User u5 = User.builder()
                .username("Yeager")
                .role(role)
                .enabled(true)
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .accountNonLocked(false)
                .reason("Toxicity, many reports")
                .bannedBy("Evi")
                .build();

        User u6 = User.builder()
                .username("Satoru")
                .email("evistifate1@gmail.com")
                .password(passwordEncoder.encode("Useruser1!"))
                .role(role)
                .accountNonLocked(false)
                .enabled(true)
                .bannedBy("Evi")
                .reason("Toxicity, trolling, not taking serious warning from administration")
                .build();

        List<User> users = Arrays.asList(u1, u2, u3, u4, u5, u6);
        userRepository.saveAll(users);


    }

}

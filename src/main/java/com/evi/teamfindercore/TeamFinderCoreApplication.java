package com.evi.teamfindercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class TeamFinderCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamFinderCoreApplication.class, args);
    }

}

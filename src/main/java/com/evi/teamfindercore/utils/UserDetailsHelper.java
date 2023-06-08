package com.evi.teamfindercore.utils;


import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.exception.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDetailsHelper {

    public static User getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        throw new UserNotFoundException("Cant load security context");
    }



}

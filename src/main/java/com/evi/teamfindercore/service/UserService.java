package com.evi.teamfindercore.service;

import com.evi.teamfindercore.model.UserDTO;
import com.evi.teamfindercore.model.UserProfileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserByUsername(String username);

    UserProfileDTO getLoggedUser();

    UserDTO updateUserByDTO(UserDTO userDTO);

    UserProfileDTO getUserProfile(Long userId);

    void changeProfilePicture(MultipartFile pictureFile);

    Resource getProfilePicture(Long userId);
}

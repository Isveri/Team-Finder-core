package com.evi.teamfindercore.controller;

import com.evi.teamfindercore.model.UserDTO;
import com.evi.teamfindercore.model.UserProfileDTO;
import com.evi.teamfindercore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping
    public ResponseEntity<UserProfileDTO> getAlreadyLoggedUser() {

        return ResponseEntity.ok(userService.getLoggedUser());
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserByDTO(userDTO));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> showUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PatchMapping(path = "/profilePicture")
    public ResponseEntity<Resource> setProfilePicture(@RequestParam("profilePicture") MultipartFile pictureFile) {
        userService.changeProfilePicture(pictureFile);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/profilePicture/{userId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long userId) {
        Resource file = userService.getProfilePicture(userId);
        if (file != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userId + "-" + file.getFilename() + "\"").body(file);
        }
        return null;
    }
}

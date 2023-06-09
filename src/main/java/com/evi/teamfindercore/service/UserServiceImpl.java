package com.evi.teamfindercore.service;

import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.exception.UserNotFoundException;
import com.evi.teamfindercore.mapper.UserMapper;
import com.evi.teamfindercore.model.UserDTO;
import com.evi.teamfindercore.model.UserProfileDTO;
import com.evi.teamfindercore.repository.UserRepository;
import com.evi.teamfindercore.utils.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.evi.teamfindercore.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserDTO)
                .toList();
    }

    @Override
    public UserDTO getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .map(userMapper::mapUserToUserDTO)
                .orElseThrow(()-> new UsernameNotFoundException("There is no user with username: "+username));

    }

    @Override
    public UserProfileDTO getLoggedUser() {

        User currentUser = getCurrentUser();
        long id = currentUser.getId();
        return userRepository.findById(id)
                .map(userMapper::mapUserToUserProfileDTO)
                .map(userDTO -> {
                    userDTO.setId(id);          //TODO sprawdzic dlaczego tak
                    return userDTO;
                })
                .orElseThrow(() -> new UserNotFoundException("User not found id:" + id));
    }

    @Override
    public UserDTO updateUserByDTO(UserDTO userDTO) {

        User user = getUserById(getCurrentUser().getId());
        //TODO dodac walidacje z hibernate - mozliwe ze trzeba bedzie nowe DTO dac narazie zostanie tak
        userDTO.setPassword(user.getPassword());

        return userMapper.mapUserToUserDTO(userMapper.updateUserFromUserDTO(userDTO, user));
    }

    @Override
    public UserProfileDTO getUserProfile(Long userId) {

        return userMapper.mapUserToUserProfileDTO(getUserById(userId));
    }

    @Override
    public void changeProfilePicture(MultipartFile pictureFile) {
        User currentUser = getCurrentUser();
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("User not found id:" + currentUser.getId()));
        user.setProfileImgName(user.getId() + "-" + FileHandler.save(pictureFile, user.getId()));
        userRepository.save(user);
    }

    @Override
    public Resource getProfilePicture(Long userId) {
        User user = getUserById(userId);
        if (Optional.ofNullable(user.getProfileImgName()).isPresent()) {
            return FileHandler.load(user.getProfileImgName());
        }
        return null;
    }

    private User getUserById(Long userId){

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }
}

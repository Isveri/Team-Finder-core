package com.evi.teamfindercore.mapper;

import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.model.BannedUserDTO;
import com.evi.teamfindercore.model.UserDTO;
import com.evi.teamfindercore.model.UserMsgDTO;
import com.evi.teamfindercore.model.UserProfileDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
@Component
@Mapper(builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserDTO mapUserToUserDTO(User user);

    public abstract UserMsgDTO mapUserToUserMsgDTO(User user);

    public abstract BannedUserDTO mapUserToBannedUserDTO(User user);

    public abstract UserProfileDTO mapUserToUserProfileDTO(User user);

    public abstract User updateUserFromUserDTO(UserDTO userDTO, @MappingTarget User user);
}

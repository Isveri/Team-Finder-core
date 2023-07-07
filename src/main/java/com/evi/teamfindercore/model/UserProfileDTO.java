package com.evi.teamfindercore.model;

import com.evi.teamfindercore.domain.UserInfo;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Setter
@Getter
public class UserProfileDTO {

    private Long id;
    private String username;
    private String email;
    private String info;
    private String name;
    private String age;
    private String phone;
    private String city;
    private RoleDTO role;
    private List<InGameRolesDTO> inGameRoles;
    private List<PlatformDTO> platforms;
}

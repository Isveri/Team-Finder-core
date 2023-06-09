package com.evi.teamfindercore.model;

import com.evi.teamfindercore.domain.UserInfo;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String info;
    private int age;
    private int phone;
    private String city;
    private RoleDTO role;
    private List<InGameRolesDTO> inGameRoles;
}

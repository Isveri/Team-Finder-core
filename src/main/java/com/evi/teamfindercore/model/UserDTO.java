package com.evi.teamfindercore.model;

import com.evi.teamfindercore.domain.UserInfo;
import lombok.*;

import javax.validation.constraints.*;
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

    @Pattern(regexp = "^[a-zA-Z]{0,20}$")
    private String name;
    @Email
    private String email;
    @Size(max=150)
    private String info;

    @Pattern(regexp = "^(\\s*|[1-9]{1}[0-9]{1})$")
    private String age;

    @Pattern(regexp = "^(\\s*|[0-9]{9}$)")
    private String phone;
    private String city;
    private RoleDTO role;
    private List<InGameRolesDTO> inGameRoles;
}

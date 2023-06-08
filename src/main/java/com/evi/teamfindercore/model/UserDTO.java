package com.evi.teamfindercore.model;

import com.evi.teamfindercore.domain.UserInfo;
import lombok.*;

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
    private String email;
    private UserInfo userInfo;
}

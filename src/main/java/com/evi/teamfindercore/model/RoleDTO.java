package com.evi.teamfindercore.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
public class RoleDTO {
    private Long id;
    private String name;
}

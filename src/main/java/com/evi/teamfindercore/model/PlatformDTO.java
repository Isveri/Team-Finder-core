package com.evi.teamfindercore.model;

import com.evi.teamfindercore.domain.Platform;
import com.evi.teamfindercore.domain.Platform.PlatformType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
@Getter
@Builder
public class PlatformDTO {

    private Long id;

    private PlatformType platformType;

    private String username;

    private String avatar;

    private String discriminator;
}

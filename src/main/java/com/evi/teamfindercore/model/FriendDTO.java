package com.evi.teamfindercore.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class FriendDTO {

    private Long id;
    private UserMsgDTO user;
    private Long chatId;
}

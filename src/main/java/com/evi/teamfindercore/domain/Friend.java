package com.evi.teamfindercore.domain;

import com.evi.teamfindercore.domain.Chat.Chat;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Builder.Default
    private boolean online = false;

    @ManyToOne()
    @JoinColumn(name="chat_id")
    private Chat chat;
}

package com.evi.teamfindercore.domain.Chat;

import com.evi.teamfindercore.domain.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class MessageStatus {

    public enum Status{
        READ,
        UNREAD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Status status = Status.UNREAD;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="message_id")
    private Message message;
}

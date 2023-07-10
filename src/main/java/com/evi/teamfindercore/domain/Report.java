package com.evi.teamfindercore.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String reason;

    @ManyToOne()
    @JoinColumn(name = "reportedUser_id")
    private User reportedUser;

    @OneToOne
    private User reportedBy;

    @NotNull
    private String date;

}

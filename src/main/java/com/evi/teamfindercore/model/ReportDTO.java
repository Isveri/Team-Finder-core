package com.evi.teamfindercore.model;


import lombok.*;
import org.h2.engine.User;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long id;
    private String reason;
    private UserMsgDTO reportedBy;
    private String date;
}

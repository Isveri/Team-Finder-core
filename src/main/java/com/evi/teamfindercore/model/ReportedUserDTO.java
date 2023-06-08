package com.evi.teamfindercore.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ReportedUserDTO {
    private UserMsgDTO reportedUser;
    private List<ReportDTO> reports = new ArrayList<>();



    public void addReport(ReportDTO reportDTO){
        this.reports.add(reportDTO);
    }


}


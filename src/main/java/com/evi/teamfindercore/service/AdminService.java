package com.evi.teamfindercore.service;

import com.evi.teamfindercore.model.BannedUserDTO;
import com.evi.teamfindercore.model.ReportedUserDTO;

import java.util.List;

public interface AdminService {
    List<ReportedUserDTO> getReportedUsers();

    void deleteReports(Long userId);

    List<BannedUserDTO> getBannedUsers();

    void unbanUser(Long userId);

    void banUser(BannedUserDTO bannedUserDTO);
}

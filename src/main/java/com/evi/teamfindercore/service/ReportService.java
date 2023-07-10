package com.evi.teamfindercore.service;

import com.evi.teamfindercore.model.ReportDTO;

public interface ReportService {

    void reportUser(ReportDTO reportDTO, Long userId);
    void deleteReports(Long userId);
}

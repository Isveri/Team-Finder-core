package com.evi.teamfindercore.controller;

import com.evi.teamfindercore.model.ReportDTO;
import com.evi.teamfindercore.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> reportUser(@RequestBody ReportDTO reportDTO, @PathVariable Long userId) {
        reportService.reportUser(reportDTO, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReports(@PathVariable Long userId) {
        reportService.deleteReports(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

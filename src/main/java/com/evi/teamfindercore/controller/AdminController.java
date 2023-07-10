package com.evi.teamfindercore.controller;

import com.evi.teamfindercore.model.BannedUserDTO;
import com.evi.teamfindercore.model.ReportedUserDTO;
import com.evi.teamfindercore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/reported")
    public ResponseEntity<List<ReportedUserDTO>> getReportedUsers() {
        return ResponseEntity.ok(adminService.getReportedUsers());
    }


    @GetMapping("/banned")
    public ResponseEntity<List<BannedUserDTO>> getBannedUsers() {
        return ResponseEntity.ok(adminService.getBannedUsers());
    }

    @PatchMapping("/banned/{userId}")
    public ResponseEntity<?> unbanUser(@PathVariable Long userId) {
        adminService.unbanUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/banned")
    public ResponseEntity<?> banUser(@RequestBody BannedUserDTO bannedUserDTO) {
        adminService.banUser(bannedUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

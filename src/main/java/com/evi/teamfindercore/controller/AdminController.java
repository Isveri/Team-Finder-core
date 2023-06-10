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
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/reportedUsers")
    public ResponseEntity<List<ReportedUserDTO>> getReportedUsers(){
        return ResponseEntity.ok(adminService.getReportedUsers());
    }

    @DeleteMapping("/deleteReports/{userId}")
    public ResponseEntity<?> deleteReports(@PathVariable Long userId){
        adminService.deleteReports(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/banned")
    public ResponseEntity<List<BannedUserDTO>> getBannedUsers(){
        return ResponseEntity.ok(adminService.getBannedUsers());
    }

    @GetMapping("/unban/{userId}")
    public ResponseEntity<?> unbanUser(@PathVariable Long userId){
        adminService.unbanUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/banUser")
    public ResponseEntity<?> banUser(@RequestBody BannedUserDTO bannedUserDTO){
        adminService.banUser(bannedUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

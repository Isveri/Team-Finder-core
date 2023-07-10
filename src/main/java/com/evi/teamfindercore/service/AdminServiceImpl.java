package com.evi.teamfindercore.service;

import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.exception.AlreadyBannedException;
import com.evi.teamfindercore.exception.UserNotFoundException;
import com.evi.teamfindercore.mapper.ReportMapper;
import com.evi.teamfindercore.mapper.UserMapper;
import com.evi.teamfindercore.model.BannedUserDTO;
import com.evi.teamfindercore.model.ReportedUserDTO;
import com.evi.teamfindercore.repository.ReportRepository;
import com.evi.teamfindercore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.evi.teamfindercore.utils.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final ReportRepository reportRepository;
    private final UserMapper userMapper;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;

    @Override
    public List<ReportedUserDTO> getReportedUsers() {

        Map<String, ReportedUserDTO> reportedUsers = new HashMap<>();

        reportRepository.findAllByReportedUserEnabled(true)
                .forEach((report) -> {
                    ReportedUserDTO reportedUserDTO = new ReportedUserDTO();
                    reportedUserDTO.setReportedUser(userMapper.mapUserToUserMsgDTO(report.getReportedUser()));
                    reportedUserDTO.addReport(reportMapper.mapReportToReportDTO(report));
                    reportedUsers.put(report.getReportedUser().getUsername(), reportedUserDTO);
                });

        return new ArrayList<>(reportedUsers.values());
    }

    @Override
    public List<BannedUserDTO> getBannedUsers() {
        return userRepository.findAllByAccountNonLockedFalse()
                .stream()
                .map(userMapper::mapUserToBannedUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void unbanUser(Long userId) {
        User user = getUserById(userId);
        user.setAccountNonLocked(true);
        user.setBannedBy(null);
        user.setReason(null);
        userRepository.save(user);
    }

    @Override
    public void banUser(BannedUserDTO bannedUserDTO) {
        User admin = getCurrentUser();
        Long userId = bannedUserDTO.getId();
        User userToBan = getUserById(userId);
        if (!userToBan.isAccountNonLocked()) {
            throw new AlreadyBannedException("User " + userToBan.getUsername() + " is already banned");
        }
        userToBan.setAccountNonLocked(false);
        userToBan.setBannedBy(admin.getUsername());
        userToBan.setReason(bannedUserDTO.getReason());
        userToBan.setReports(null);
        reportRepository.deleteAll(reportRepository.findAllByReportedUserId(userId));
        userRepository.save(userToBan);
    }

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }
}

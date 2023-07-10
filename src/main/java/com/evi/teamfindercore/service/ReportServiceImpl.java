package com.evi.teamfindercore.service;

import com.evi.teamfindercore.domain.Report;
import com.evi.teamfindercore.domain.User;
import com.evi.teamfindercore.exception.AlreadyReportedException;
import com.evi.teamfindercore.exception.UserNotFoundException;
import com.evi.teamfindercore.mapper.ReportMapper;
import com.evi.teamfindercore.model.ReportDTO;
import com.evi.teamfindercore.repository.ReportRepository;
import com.evi.teamfindercore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.evi.teamfindercore.utils.UserDetailsHelper.getCurrentUser;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    private User getUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found id:" + userId));
    }

    @Override
    public void reportUser(ReportDTO reportDTO, Long userId) {

        User reportedBy = getCurrentUser();
        User userToReport = getUserById(userId);

        if (!reportRepository.existsByReportedByIdAndReportedUserId(reportedBy.getId(), userToReport.getId())) {
            Report report = reportMapper.mapReportDTOToReport(reportDTO);
            report.setReportedBy(reportedBy);
            userToReport.getReports().add(report);
            report.setReportedUser(userToReport);
            report.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            reportRepository.save(report);
        } else {
            throw new AlreadyReportedException("You already reported this user");
        }
    }

    @Override
    public void deleteReports(Long userId) {
        User user = getUserById(userId);
        user.setReports(null);
        reportRepository.deleteAll(reportRepository.findAllByReportedUserId(userId));
        userRepository.save(user);
    }


}

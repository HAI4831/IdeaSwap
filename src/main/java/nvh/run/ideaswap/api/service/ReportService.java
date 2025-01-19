package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IReport;
import nvh.run.ideaswap.api.service.intf.IUserService;
import nvh.run.ideaswap.data.dto.ReportDTO;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.entity.Reports;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import nvh.run.ideaswap.data.repository.ManagerRepository;
import nvh.run.ideaswap.data.repository.ReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportService implements IReport {
    ReportRepository reportRepository;
    IUserService IUserService;
    IUserRepository IUserRepository;
    ManagerService ManagerService;
    ManagerRepository ManagerRepository;

    @Override
    public ResponseEntity<Object> getAllReports() {
        List<Reports> reports;
        try {
            reports = reportRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("success", false, "message", "Retrieve List Reports failed", "error", e.getMessage())
            );
        }
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve List Reports successfully", "data", reports)
        );
    }

    @Override
    public ResponseEntity<Object> getReportById(String id) {
        Reports report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve Report By ID successfully", "data", report)
        );
    }

    @Override
    public ResponseEntity<Object> createReport(ReportDTO reportDTO) {
        Users user = IUserRepository.findById(reportDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
//        Users user = IUserService.getUserById(reportDTO.getUserId());
        Managers manager = ManagerRepository.findById(reportDTO.getModeratorId()).orElseThrow(() -> new RuntimeException("Manager not found"));
        Reports savedReport = reportRepository.save(
                Reports.builder()
                        .referenceID(reportDTO.getReferenceId())
                        .userID(user)
                        .type(reportDTO.getType())
                        .status(reportDTO.getStatus())
                        .moderatorID(manager)
                        .content(reportDTO.getContent())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Create Report successfully", "data", savedReport)
        );
    }

    @Override
    public ResponseEntity<Object> updateReport(String id, ReportDTO reportDTO) {
        getReportById(id);
        Managers manager = ManagerRepository.findById(reportDTO.getModeratorId()).orElseThrow(() -> new RuntimeException("Manager not found"));
        Users user = IUserRepository.findById(reportDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
//        Users user = IUserService.getUserById(reportDTO.getUserId());
        Reports updatedReport = reportRepository.save(
                Reports.builder()
                        .referenceID(reportDTO.getReferenceId())
                        .userID(user)
                        .type(reportDTO.getType())
                        .status(reportDTO.getStatus())
                        .moderatorID(manager)
                        .content(reportDTO.getContent())
                        .build()
        );
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Update Report successfully", "data", updatedReport)
        );
    }

    @Override
    public ResponseEntity<Object> deleteReport(String id) {
        getReportById(id);
        reportRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Delete Report successfully"));
    }
}

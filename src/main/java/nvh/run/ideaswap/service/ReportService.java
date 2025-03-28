package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ReportRequest;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.entity.Report;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.ReportRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportService {
    ReportRepository reportRepository;
    UserService userService;
    ManagerService managerService;

//    @Cacheable(value = "reports",key = "'page:' + #page + ':size:' + #size")
    public Page<Report> getAllReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reports;
        try {
            reports = reportRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all reports failed",e);
        }
        return reports;
    }
//    @Cacheable(value="reports")
    public List<Report> getAllReports() {
        List<Report> reports;
        try {
            reports = reportRepository.findAll();
        } catch (Exception e) {
           throw new RuntimeException("Get all reports failed",e);
        }
        return reports;
    }

    @Cacheable(value="report",key="#id",condition = "#id!=null")
    public Report getReportById(String id) {
        Report report ;
        try {
            report = reportRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Report not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get report failed",e);
        }
        return report;
    }

    @CachePut(value="report",key="#reportRequest.id",condition = "#reportRequest.id!=null")
    public Report createReport(ReportRequest reportRequest) {
        User user = userService.getUserById(reportRequest.getUserID());
        Manager manager = managerService.getManagerById(reportRequest.getModeratorID());
        Report report = Report.builder()
                .id(reportRequest.getId())
                .userID(user.getId())
                .moderatorID(manager.getId())
                .content(reportRequest.getContent())
                .referenceID(reportRequest.getReferenceID())
                .type(reportRequest.getType())
                .status(reportRequest.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        try {
            report = reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException("Create report failed",e);
        }
        return report;
    }

    @CachePut(value="report",key="#id",condition = "#id!=null")
    public Report updateReport(String id, ReportRequest reportRequest) {
        getReportById(id);
        User user = userService.getUserById(reportRequest.getUserID());
        Manager manager = managerService.getManagerById(reportRequest.getModeratorID());
        Report report = Report.builder()
                .id(id)
                .userID(user.getId())
                .moderatorID(manager.getId())
                .content(reportRequest.getContent())
                .referenceID(reportRequest.getReferenceID())
                .type(reportRequest.getType())
                .status(reportRequest.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        try {
            report = reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException("Update report failed",e);
        }
        return report;
    }

    @CacheEvict(value="report",key="#id",condition = "#id!=null")
    public Report deleteReport(String id) {
        Report report= getReportById(id);
        try {
            reportRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete report failed",e);
        }
        return report;
    }
}

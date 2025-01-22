package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Reports;
import nvh.run.ideaswap.data.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportService {
    ReportRepository reportRepository;

    public List<Reports> getAllReports() {
        List<Reports> reports;
        try {
            reports = reportRepository.findAll();
        } catch (Exception e) {
           throw new RuntimeException("Get all reports failed",e);
        }
        return reports;
    }

    public Reports getReportById(String id) {
        Reports report ;
        try {
            report = reportRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Report not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get report failed",e);
        }
        return report;
    }

    public Reports createReport(Reports report) {
        Reports savedReport ;
        try {
            savedReport = reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException("Create report failed",e);
        }
        return savedReport;
    }

    public Reports updateReport(String id, Reports report) {
        getReportById(id);
        Reports updatedReport ;
        try {
            updatedReport = reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException("Update report failed",e);
        }
        return updatedReport;
    }

    public Reports deleteReport(String id) {
        Reports report= getReportById(id);
        try {
            reportRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete report failed",e);
        }
        return report;
    }
}

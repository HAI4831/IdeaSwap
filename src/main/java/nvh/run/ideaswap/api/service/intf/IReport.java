package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.ReportDTO;
import org.springframework.http.ResponseEntity;

public interface IReport {
    ResponseEntity<Object> getAllReports();
    ResponseEntity<Object> getReportById(String id);
    ResponseEntity<Object> createReport(ReportDTO reportDTO);
    ResponseEntity<Object> updateReport(String id, ReportDTO reportDTO);
    ResponseEntity<Object> deleteReport(String id);
}


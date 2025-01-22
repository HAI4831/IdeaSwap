package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Reports;
import nvh.run.ideaswap.service.ReportService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Object> getAllReports() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.getAllReports()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReportById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.getReportById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createReport(@RequestBody Reports report) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.createReport(report)
                )
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> updateReport(@PathVariable ObjectId id, @RequestBody Reports report) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.updateReport(id,report)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReport(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.deleteReport(id)
                )
        );
    }
}

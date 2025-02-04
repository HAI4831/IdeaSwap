package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.ReportRequest;
import nvh.run.ideaswap.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<Object> getReportById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.getReportById(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createReport(@RequestBody ReportRequest reportRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.createReport(reportRequest)
                )
        );
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateReport(@PathVariable String id, @RequestBody ReportRequest reportRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.updateReport(id,reportRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteReport(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Reports successfully",
                        "report", reportService.deleteReport(id)
                )
        );
    }
}

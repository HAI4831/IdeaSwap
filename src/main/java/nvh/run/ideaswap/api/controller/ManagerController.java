package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.ManagerRequest;
import nvh.run.ideaswap.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "managers", managerService.getAllManagers()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getManagerById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Retrieve Manager By ID successfully",
                "manager", managerService.getManagerById(id)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createManager(@RequestBody @Valid ManagerRequest managerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Manager created successfully",
                        "manager", managerService.createManager(managerRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateManager(@PathVariable String id, @RequestBody @Valid ManagerRequest managerRequest) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager updated successfully",
                "manager", managerService.updateManager(id, managerRequest)
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteManager(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager deleted successfully",
                "manager", managerService.deleteManager(id)
        ));
    }
}


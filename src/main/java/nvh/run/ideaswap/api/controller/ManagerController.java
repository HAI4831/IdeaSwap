package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Managers;
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

    @PostMapping
    public ResponseEntity<Object> createManager(@RequestBody @Valid Managers manager) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Manager created successfully",
                        "manager", managerService.createManager(manager)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManager(@PathVariable String id, @RequestBody @Valid Managers manager) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager updated successfully",
                "manager", managerService.updateManager(id, manager)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteManager(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager deleted successfully",
                "manager", managerService.deleteManager(id)
        ));
    }
}


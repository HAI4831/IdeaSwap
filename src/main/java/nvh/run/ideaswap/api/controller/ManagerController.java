package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.ManagerService;
import nvh.run.ideaswap.data.dto.ManagerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getManagerById(@PathVariable String id) {
        return managerService.getManagerById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createManager(@RequestBody @Valid ManagerDTO managerDTO) {
        return managerService.createManager(managerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManager(@PathVariable String id, @RequestBody @Valid ManagerDTO managerDTO) {
        return managerService.updateManager(id, managerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteManager(@PathVariable String id) {
        return managerService.deleteManager(id);
    }
}


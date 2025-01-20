package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import nvh.run.ideaswap.service.CensorshipsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/censorships")
@RequiredArgsConstructor
public class CensorshipsController {
    private final CensorshipsService censorshipsService;

    @GetMapping
    public ResponseEntity<Object> getAllCensorships() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Censorships successfully",
                        "censorships", censorshipsService.getAllCensorships()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCensorshipById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Censorship successfully",
                        "censorship", censorshipsService.getCensorshipById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createCensorship(@Valid @RequestBody CensorshipsDTO censorshipsDTO) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Censorship created successfully",
                        "censorship", censorshipsService.createCensorship(censorshipsDTO)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCensorship(@PathVariable String id, @Valid @RequestBody CensorshipsDTO censorshipsDTO) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship updated successfully",
                        "censorship", censorshipsService.updateCensorship(id, censorshipsDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCensorship(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship deleted successfully",
                        "censorship",censorshipsService.deleteCensorship(id)
                )
        );
    }
}

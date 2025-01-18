package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.ICensorships;
import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/censorships")
@RequiredArgsConstructor
public class CensorshipsController {
    private final ICensorships censorshipsService;

    @GetMapping
    public ResponseEntity<Object> getAllCensorships() {
        return censorshipsService.getAllCensorships();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCensorshipById(@PathVariable String id) {
        return censorshipsService.getCensorshipById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createCensorship(@Valid @RequestBody CensorshipsDTO censorshipsDTO) {
        return censorshipsService.createCensorship(censorshipsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCensorship(@PathVariable String id, @Valid @RequestBody CensorshipsDTO censorshipsDTO) {
        return censorshipsService.updateCensorship(id, censorshipsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCensorship(@PathVariable String id) {
        return censorshipsService.deleteCensorship(id);
    }
}

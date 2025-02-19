package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Censorships;
import nvh.run.ideaswap.service.CensorshipsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
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

    @PutMapping("/update")
    public ResponseEntity<Object> updateCensorshipByContentID(@Valid @RequestBody Censorships censorship) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship updated successfully",
                        "censorship", censorshipsService.updateCensorshipByContentID(censorship)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCensorship(@PathVariable String id, @Valid @RequestBody Censorships censorship) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship updated successfully",
                        "censorship", censorshipsService.updateCensorship(id, censorship)
                )
        );
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getCensorshipById(@PathVariable String id) {
//        return ResponseEntity.ok(
//                Map.of(
//                        "success", true,
//                        "message", "Retrieve Censorship successfully",
//                        "censorship", censorshipsService.getCensorshipById(id)
//                )
//        );
//    }
//    @PostMapping("/add")
//    public ResponseEntity<Object> createCensorship(@Valid @RequestBody Censorships censorship) {
//        return ResponseEntity.status(201).body(
//                Map.of(
//                        "success", true,
//                        "message", "Censorship created successfully",
//                        "censorship", censorshipsService.createCensorship(censorship)
//                )
//        );
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> deleteCensorship(@PathVariable String id) {
//        return ResponseEntity.ok(
//                Map.of(
//                        "success", true,
//                        "message", "Censorship deleted successfully",
//                        "censorship",censorshipsService.deleteCensorship(id)
//                )
//        );
//    }
}

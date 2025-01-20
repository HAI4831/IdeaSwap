package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.HeartDTO;
import nvh.run.ideaswap.service.HeartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @GetMapping
    public ResponseEntity<Object> getAllHearts() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve hearts successfully",
                        "hearts", heartService.getAllHearts()
                )
        );
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<Object> getHeartsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve hearts by user successfully",
                        "hearts", heartService.getHeartsByUserID(userID)
                )
        );
    }

    @GetMapping("/reference/{referenceID}")
    public ResponseEntity<Object> getHeartsByReferenceID(@PathVariable String referenceID) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Heart created successfully"
                        , "heart", heartService.getHeartsByReferenceID(referenceID)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createHeart(@Valid @RequestBody HeartDTO heartDTO) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Heart deleted successfully",
                        "heart", heartService.createHeart(heartDTO)
                ));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHeart(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve hearts by reference successfully",
                        "hearts", heartService.deleteHeart(id)
                )
        );
    }
}

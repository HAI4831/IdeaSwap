package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IHeartService;
import nvh.run.ideaswap.data.dto.HeartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/hearts")
@RequiredArgsConstructor
public class HeartController {
    private final IHeartService heartService;

    @GetMapping
    public ResponseEntity<Object> getAllHearts() {
        return heartService.getAllHearts();
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<Object> getHeartsByUserID(@PathVariable String userID) {
        return heartService.getHeartsByUserID(userID);
    }

    @GetMapping("/reference/{referenceID}")
    public ResponseEntity<Object> getHeartsByReferenceID(@PathVariable String referenceID) {
        return heartService.getHeartsByReferenceID(referenceID);
    }

    @PostMapping
    public ResponseEntity<Object> createHeart(@Valid @RequestBody HeartDTO heartDTO) {
        return heartService.createHeart(heartDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHeart(@PathVariable String id) {
        return heartService.deleteHeart(id);
    }
}

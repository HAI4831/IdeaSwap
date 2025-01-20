package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.FollowDTO;
import nvh.run.ideaswap.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping
    public ResponseEntity<Object> getAllFollows() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve follows successfully",
                        "follows", followService.getAllFollows()
                )
        );
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Object> getFollowsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve follows by user successfully",
                        "follows", followService.getFollowsByUserID(userID)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createFollow(@Valid @RequestBody FollowDTO followDTO) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Follow created successfully",
                        "follow", followService.createFollow(followDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFollow(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Follow deleted successfully",
                        "follow",followService.deleteFollow(id)
                )
        );
    }

    @GetMapping("/followers/{userID}")
    public ResponseEntity<Object> getFollowersByUserID(@PathVariable String userID) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve followers successfully",
                        "followers", followService.getFollowersByUserID(userID)
                )
        );
    }
}

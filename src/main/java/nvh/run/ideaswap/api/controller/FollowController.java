package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Follows;
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
                        "message", "Retrieve follow list successfully",
                        "follows", followService.getAllFollows()
                )
        );
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Object> getFollowsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve follows by user ID successfully",
                        "follows", followService.getFollowsByUserID(userID)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createFollow(@Valid @RequestBody Follows follow) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Follow created successfully",
                        "follow", followService.createFollow(follow)
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
                        "message", "Retrieve followers by user ID successfully",
                        "followers", followService.getFollowsByUserID(userID)
                )
        );
    }
}

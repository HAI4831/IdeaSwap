package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IFollowService;
import nvh.run.ideaswap.data.dto.FollowDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {
    private final IFollowService followService;

    @GetMapping
    public ResponseEntity<Object> getAllFollows() {
        return followService.getAllFollows();
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Object> getFollowsByUserID(@PathVariable String userID) {
        return followService.getFollowsByUserID(userID);
    }

    @PostMapping
    public ResponseEntity<Object> createFollow(@Valid @RequestBody FollowDTO followDTO) {
        return followService.createFollow(followDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFollow(@PathVariable String id) {
        return followService.deleteFollow(id);
    }

    @GetMapping("/followers/{userID}")
    public ResponseEntity<Object> getFollowersByUserID(@PathVariable String userID) {
        return followService.getFollowersByUserID(userID);
    }
}

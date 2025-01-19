package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IFollowService;
import nvh.run.ideaswap.api.service.intf.IUserService;
import nvh.run.ideaswap.data.dto.FollowDTO;
import nvh.run.ideaswap.data.entity.Follows;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.FollowRepository;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FollowService implements IFollowService {
    FollowRepository followRepository;
    IUserService iUserService;
    IUserRepository iUserRepository;

    @Override
    public ResponseEntity<Object> getAllFollows() {
        List<Follows> follows = followRepository.findAll();
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve follows successfully", "follows", follows)
        );
    }

    @Override
    public ResponseEntity<Object> getFollowsByUserID(String userID) {
        List<Follows> follows = followRepository.findByUserID_Id(userID);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve follows by user successfully", "follows", follows)
        );
    }

    @Override
    public ResponseEntity<Object> createFollow(FollowDTO followDTO) {
        Users user= iUserRepository.findById(followDTO.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
        Follows follow = Follows.builder()
                .followerID(followDTO.getFollowerID())
                .userID(user)  // Assuming `Users` has a no-arg constructor
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Follows savedFollow = followRepository.save(follow);
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Follow created successfully", "follow", savedFollow)
        );
    }

    @Override
    public ResponseEntity<Object> deleteFollow(String id) {
        Follows follow = followRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Follow not found"));
        followRepository.delete(follow);
        return ResponseEntity.ok(Map.of("success", true, "message", "Follow deleted successfully"));
    }

    @Override
    public ResponseEntity<Object> getFollowersByUserID(String userID) {
        List<Follows> followers = followRepository.findByUserID_Id(userID);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve followers successfully", "followers", followers)
        );
    }
}

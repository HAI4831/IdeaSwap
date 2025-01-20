package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class FollowService {
    FollowRepository followRepository;
    UserService iUserService;
    IUserRepository iUserRepository;

    public FollowDTO getAllFollows() {
        List<Follows> follows = followRepository.findAll();
        return FollowDTO.builder().build();
    }

    public FollowDTO getFollowsByUserID(String userID) {
        List<Follows> follows = followRepository.findByUserID_Id(userID);
        return FollowDTO.builder().build();
    }

    public FollowDTO createFollow(FollowDTO followDTO) {
        Users user= iUserRepository.findById(followDTO.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
        Follows follow = Follows.builder()
                .followerID(followDTO.getFollowerID())
                .userID(user)  // Assuming `Users` has a no-arg constructor
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Follows savedFollow = followRepository.save(follow);
        return FollowDTO.builder().build();
    }

    public FollowDTO deleteFollow(String id) {
        Follows follow = followRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Follow not found"));
        followRepository.delete(follow);
        return FollowDTO.builder().build();
    }

    public FollowDTO getFollowersByUserID(String userID) {
        List<Follows> followers = followRepository.findByUserID_Id(userID);
        return FollowDTO.builder().build();
    }
}

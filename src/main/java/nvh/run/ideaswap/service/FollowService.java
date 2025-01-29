package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.FollowRequest;
import nvh.run.ideaswap.data.entity.Follows;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FollowService {
    FollowRepository followRepository;
    UserService userService;

    public List<Follows> getAllFollows() {
        List<Follows> follows;
        try {
            follows = followRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all follows failed",e);
        }
        return follows;
    }

    public List<Follows> getFollowsByUserID(String userID) {
        List<Follows> follows ;
        try {
            follows = followRepository.findByUserID_Id(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get follows failed",e);
        }
        return follows;
    }
    public Follows getFollowById(String id) {
        Follows follow;
        try {
            follow = followRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Follow not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get follow failed",e);
        }
        return follow;
    }

    public Follows createFollow(FollowRequest followRequest) {
//        private String id;
//        @IsObjectID
//        private String followerID;
//        @IsObjectID
//        private String userID;
//        private LocalDateTime createdAt;
//        private LocalDateTime updatedAt;
        Users user=userService.getUserById(followRequest.getUserID());
        Users follower=userService.getUserById(followRequest.getFollowerID());
        Follows follow;
        try {
            follow = followRepository.save(
                    Follows.builder()
                            .id(followRequest.getId())
                            .followerID(follower)
                            .userID(user)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create follow failed",e);
        }
        return follow;
    }

    public Follows deleteFollow(String id) {
        Follows follow = getFollowById(id);
        try {
            followRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete follow failed",e);
        }
        return follow;
    }
}

package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.FollowRequest;
import nvh.run.ideaswap.data.entity.Follow;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.FollowRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FollowService {
    FollowRepository followRepository;
    UserService userService;

//    @Cacheable(value = "follows",key = "'page:' + #page + ':size:' + #size")
    public Page<Follow> getAllFollows(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> follows;
        try {
            follows = followRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all follows failed",e);
        }
        return follows;
    }
//    @Cacheable("follows")
    public List<Follow> getAllFollows() {
        List<Follow> follows;
        try {
            follows = followRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all follows failed",e);
        }
        return follows;
    }

    @Cacheable(value="follow",key="#userID",condition = "#userID!=null")
    public List<Follow> getFollowsByUserID(String userID) {
        List<Follow> follows ;
        try {
            follows = followRepository.findByUserID(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get follows failed",e);
        }
        return follows;
    }
    @Cacheable(value = "follow",key="#id",condition = "#id!=null")
    public Follow getFollowById(String id) {
        Follow follow;
        try {
            follow = followRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Follow not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get follow failed",e);
        }
        return follow;
    }

    @CachePut(value="follow",key="#followRequest.id",condition = "#followRequest.id!=null")
    public Follow createFollow(FollowRequest followRequest) {
        User user=userService.getUserById(followRequest.getUserID());
        User follower=userService.getUserById(followRequest.getFollowerID());
        Follow follow;
        try {
            follow = followRepository.save(
                    Follow.builder()
                            .id(followRequest.getId())
                            .followerID(follower.getId())
                            .userID(user.getId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create follow failed",e);
        }
        return follow;
    }

    @CacheEvict(value="follow",key="#id",condition = "#id!=null")
    public Follow deleteFollowById(String id) {
        Follow follow = getFollowById(id);
        try {
            followRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete follow failed",e);
        }
        return follow;
    }

    @Transactional
    public Follow deleteFollow(FollowRequest followRequest) {
        Optional<Follow> followOpt = followRepository.findByFollowerIDAndUserID(
                followRequest.getFollowerID(),
                followRequest.getUserID());

        if (followOpt.isEmpty()) {
            throw new RuntimeException("Delete failed, Follow not found with follower ID: "
                    + followRequest.getFollowerID() + " and user ID: " + followRequest.getUserID());
        }

        followRepository.delete(followOpt.get());
        return followOpt.get();
    }

}

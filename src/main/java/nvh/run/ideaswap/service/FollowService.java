package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.FollowRequest;
import nvh.run.ideaswap.data.entity.Follows;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.FollowRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FollowService {
    FollowRepository followRepository;
    UserService userService;

//    @Cacheable(value = "follows",key = "'page:' + #page + ':size:' + #size")
    public Page<Follows> getAllFollows(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Follows> follows;
        try {
            follows = followRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all follows failed",e);
        }
        return follows;
    }
//    @Cacheable("follows")
    public List<Follows> getAllFollows() {
        List<Follows> follows;
        try {
            follows = followRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all follows failed",e);
        }
        return follows;
    }

    @Cacheable(value="follow",key="#userID",condition = "#userID!=null")
    public List<Follows> getFollowsByUserID(String userID) {
        List<Follows> follows ;
        try {
            follows = followRepository.findByUserID(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get follows failed",e);
        }
        return follows;
    }
    @Cacheable(value = "follow",key="#id",condition = "#id!=null")
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

    @CachePut(value="follow",key="#followRequest.id",condition = "#followRequest.id!=null")
    public Follows createFollow(FollowRequest followRequest) {
        Users user=userService.getUserById(followRequest.getUserID());
        Users follower=userService.getUserById(followRequest.getFollowerID());
        Follows follow;
        try {
            follow = followRepository.save(
                    Follows.builder()
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

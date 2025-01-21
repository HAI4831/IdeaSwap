package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Follows;
import nvh.run.ideaswap.data.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FollowService {
    FollowRepository followRepository;

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

    public Follows createFollow(Follows follow) {
        try {
            follow = followRepository.save(follow);
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

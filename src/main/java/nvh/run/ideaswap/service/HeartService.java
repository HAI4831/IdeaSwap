package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.HeartRequest;
import nvh.run.ideaswap.data.entity.Heart;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.HeartRepository;
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

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HeartService {
    HeartRepository heartRepository;
    UserService userService;

//    @Cacheable(value = "hearts",key = "'page:' + #page + ':size:' + #size")
    public Page<Heart> getAllHearts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Heart> hearts;
        try {
            hearts = heartRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }
//    @Cacheable(value="hearts")
    public List<Heart> getAllHearts() {
        List<Heart> hearts ;
        try {
            hearts = heartRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

//    @Cacheable(value="hearts",key="#userID",condition = "#userID!=null")
    public List<Heart> getHeartsByUserID(String userID) {
        List<Heart> hearts ;
        try {
            hearts = heartRepository.findByUserID(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    @CachePut(value="heart",key="#heartRequest.id",condition = "#heartRequest.id!=null")
    public Heart createHeart(HeartRequest heartRequest) {
        User user = userService.getUserById(heartRequest.getUserID());
        Heart heart;
        try {
            heart = heartRepository.save(
                    Heart.builder()
                            .userID(user.getId())
                            .referenceID(heartRequest.getReferenceID())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create heart failed",e);
        }
        return heart;
    }

    @CacheEvict(value="heart",key="#id",condition = "#id!=null")
    public Heart deleteHeart(HeartRequest heartRequest) {
         Heart heart = heartRepository.findByUserIDAndReferenceID(heartRequest.getUserID(), heartRequest.getReferenceID());
        try {
            heartRepository.delete(heart);
        } catch (Exception e) {
            throw new RuntimeException("Delete heart failed",e);
        }
        return heart;
    }

    @Cacheable(value="heart",key="#id",condition = "#id!=null")
    public Heart getHeartById(String id) {
        Heart heart;
        try {
            heart = heartRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Heart not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get heart failed",e);
        }
        return heart;
    }

    @Cacheable(value="hearts",key="#referenceID",condition = "#referenceID!=null")
    public List<Heart> getHeartsByReferenceID(String referenceID) {
        List<Heart> hearts ;
        try {
           hearts = heartRepository.findByReferenceID(referenceID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }
}

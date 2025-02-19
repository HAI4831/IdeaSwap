package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.HeartRequest;
import nvh.run.ideaswap.data.entity.Hearts;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.HeartsRepository;
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
    HeartsRepository heartsRepository;
    UserService userService;

//    @Cacheable(value = "hearts",key = "'page:' + #page + ':size:' + #size")
    public Page<Hearts> getAllHearts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hearts> hearts;
        try {
            hearts = heartsRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }
//    @Cacheable(value="hearts")
    public List<Hearts> getAllHearts() {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

//    @Cacheable(value="hearts",key="#userID",condition = "#userID!=null")
    public List<Hearts> getHeartsByUserID(String userID) {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findByUserID(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    @CachePut(value="heart",key="#heartRequest.id",condition = "#heartRequest.id!=null")
    public Hearts createHeart(HeartRequest heartRequest) {
        Users user = userService.getUserById(heartRequest.getUserID());
        Hearts heart;
        try {
            heart = heartsRepository.save(
                    Hearts.builder()
                            .id(heartRequest.getId())
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
    public Hearts deleteHeart(String id) {
         Hearts heart = getHeartById(id);
        try {
            heartsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete heart failed",e);
        }
        return heart;
    }

    @Cacheable(value="heart",key="#id",condition = "#id!=null")
    public Hearts getHeartById(String id) {
        Hearts heart;
        try {
            heart = heartsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Heart not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get heart failed",e);
        }
        return heart;
    }

    @Cacheable(value="hearts",key="#referenceID",condition = "#referenceID!=null")
    public List<Hearts> getHeartsByReferenceID(String referenceID) {
        List<Hearts> hearts ;
        try {
           hearts = heartsRepository.findByReferenceID(referenceID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }
}

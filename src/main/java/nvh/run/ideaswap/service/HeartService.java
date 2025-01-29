package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.HeartRequest;
import nvh.run.ideaswap.data.entity.Hearts;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.HeartsRepository;
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

    public List<Hearts> getAllHearts() {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    public List<Hearts> getHeartsByUserID(String userID) {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findByUserID_Id(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    public Hearts createHeart(HeartRequest heartRequest) {
        Users user = userService.getUserById(heartRequest.getUserID());
        Hearts heart;
        try {
            heart = heartsRepository.save(
                    Hearts.builder()
                            .id(heartRequest.getId())
                            .userID(user)
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

    public Hearts deleteHeart(String id) {
         Hearts heart = getHeartById(id);
        try {
            heartsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete heart failed",e);
        }
        return heart;
    }

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

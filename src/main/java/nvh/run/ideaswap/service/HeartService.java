package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Hearts;
import nvh.run.ideaswap.data.repository.HeartsRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HeartService {
    HeartsRepository heartsRepository;

    public List<Hearts> getAllHearts() {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    public List<Hearts> getHeartsByUserID(ObjectId userID) {
        List<Hearts> hearts ;
        try {
            hearts = heartsRepository.findByUserID(userID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }

    public Hearts createHeart(Hearts heart) {
        try {
            heart = heartsRepository.save(heart);
        } catch (Exception e) {
            throw new RuntimeException("Create heart failed",e);
        }
        return heart;
    }

    public Hearts deleteHeart(ObjectId id) {
         Hearts heart = getHeartById(id);
        try {
            heartsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete heart failed",e);
        }
        return heart;
    }

    public Hearts getHeartById(ObjectId id) {
        Hearts heart;
        try {
            heart = heartsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Heart not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get heart failed",e);
        }
        return heart;
    }

    public List<Hearts> getHeartsByReferenceID(ObjectId referenceID) {
        List<Hearts> hearts ;
        try {
           hearts = heartsRepository.findByReferenceID(referenceID);
        } catch (Exception e) {
            throw new RuntimeException("Get all hearts failed",e);
        }
        return hearts;
    }
}

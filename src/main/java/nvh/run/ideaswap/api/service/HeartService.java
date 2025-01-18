package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IHeartService;
import nvh.run.ideaswap.data.dto.HeartDTO;
import nvh.run.ideaswap.data.entity.Hearts;
import nvh.run.ideaswap.data.repository.HeartsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HeartService implements IHeartService {
    HeartsRepository heartsRepository;

    @Override
    public ResponseEntity<Object> getAllHearts() {
        List<Hearts> hearts = heartsRepository.findAll();
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve hearts successfully", "hearts", hearts)
        );
    }

    @Override
    public ResponseEntity<Object> getHeartsByUserID(String userID) {
        List<Hearts> hearts = heartsRepository.findByUserID_Id(userID);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve hearts by user successfully", "hearts", hearts)
        );
    }

    @Override
    public ResponseEntity<Object> createHeart(HeartDTO heartDTO) {
        Hearts heart = heartsRepository.save(
                Hearts.builder()
                        .userID(heartDTO.getUserID())
                        .referenceID(heartDTO.getReferenceID())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Heart created successfully", "heart", heart)
        );
    }

    @Override
    public ResponseEntity<Object> deleteHeart(String id) {
        getHeartById(id);
        heartsRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Heart deleted successfully"));
    }

    public Hearts getHeartById(String id) {
        return heartsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Heart not found"));
    }

    @Override
    public ResponseEntity<Object> getHeartsByReferenceID(String referenceID) {
        List<Hearts> hearts = heartsRepository.findByReferenceID(referenceID);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve hearts by reference successfully", "hearts", hearts)
        );
    }
}

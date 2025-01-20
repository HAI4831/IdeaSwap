package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.HeartDTO;
import nvh.run.ideaswap.data.entity.Hearts;
import nvh.run.ideaswap.data.repository.HeartsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HeartService {
    HeartsRepository heartsRepository;

    public HeartDTO getAllHearts() {
        List<Hearts> hearts = heartsRepository.findAll();
        return HeartDTO.builder().build();
    }

    public HeartDTO getHeartsByUserID(String userID) {
        List<Hearts> hearts = heartsRepository.findByUserID_Id(userID);
        return HeartDTO.builder().build();
    }

    public HeartDTO createHeart(HeartDTO heartDTO) {
        Hearts heart = heartsRepository.save(
                Hearts.builder()
                        .userID(heartDTO.getUserID())
                        .referenceID(heartDTO.getReferenceID())
                        .build()
        );
        return HeartDTO.builder().build();
    }

    public HeartDTO deleteHeart(String id) {
        getHeartById(id);
        heartsRepository.deleteById(id);
        return HeartDTO.builder().build();
    }

    public Hearts getHeartById(String id) {
        return heartsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Heart not found"));
    }

    public HeartDTO getHeartsByReferenceID(String referenceID) {
        List<Hearts> hearts = heartsRepository.findByReferenceID(referenceID);
        return HeartDTO.builder().build();
    }
}

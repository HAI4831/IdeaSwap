package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import nvh.run.ideaswap.data.entity.Censorships;
import nvh.run.ideaswap.data.entity.Status;
import nvh.run.ideaswap.data.repository.CensorshipsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CensorshipsService {
    CensorshipsRepository censorshipsRepository;

    public CensorshipsDTO getAllCensorships() {
        List<Censorships> censorships = censorshipsRepository.findAll();
        return CensorshipsDTO.builder().build();
    }

    public CensorshipsDTO getCensorshipById(String id) {
        Censorships censorship = censorshipsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Censorship not found"));
        return CensorshipsDTO.builder().build();
    }

    public CensorshipsDTO createCensorship(CensorshipsDTO censorshipsDTO) {
        Censorships censorship = censorshipsRepository.save(
                Censorships.builder()
                        .contentID(censorshipsDTO.getContentID())
                        .status(censorshipsDTO.getStatus() == null ? Status.PENDING : censorshipsDTO.getStatus())
                        .feedback(censorshipsDTO.getFeedback())
                        .build()
        );
        return CensorshipsDTO.builder().build();
    }

    public CensorshipsDTO updateCensorship(String id, CensorshipsDTO censorshipsDTO) {
        getCensorshipById(id);
        Censorships updatedCensorship = censorshipsRepository.save(
                Censorships.builder()
                        .id(id)
                        .contentID(censorshipsDTO.getContentID())
                        .status(censorshipsDTO.getStatus())
                        .feedback(censorshipsDTO.getFeedback())
                        .build()
        );
        return CensorshipsDTO.builder().build();
    }

    public CensorshipsDTO deleteCensorship(String id) {
        getCensorshipById(id);
        censorshipsRepository.deleteById(id);
        return CensorshipsDTO.builder().build();
    }
}

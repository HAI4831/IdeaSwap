package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.ICensorships;
import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import nvh.run.ideaswap.data.entity.Censorships;
import nvh.run.ideaswap.data.entity.Status;
import nvh.run.ideaswap.data.repository.CensorshipsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CensorshipsService implements ICensorships {
    CensorshipsRepository censorshipsRepository;

    @Override
    public ResponseEntity<Object> getAllCensorships() {
        List<Censorships> censorships = censorshipsRepository.findAll();
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Censorships successfully",
                        "censorships", censorships
                )
        );
    }

    @Override
    public ResponseEntity<Object> getCensorshipById(String id) {
        Censorships censorship = censorshipsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Censorship not found"));
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Censorship successfully",
                        "censorship", censorship
                )
        );
    }

    @Override
    public ResponseEntity<Object> createCensorship(CensorshipsDTO censorshipsDTO) {
        Censorships censorship = censorshipsRepository.save(
                Censorships.builder()
                        .contentID(censorshipsDTO.getContentID())
                        .status(censorshipsDTO.getStatus() == null ? Status.pending : censorshipsDTO.getStatus())
                        .feedback(censorshipsDTO.getFeedback())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Censorship created successfully",
                        "censorship", censorship
                )
        );
    }

    @Override
    public ResponseEntity<Object> updateCensorship(String id, CensorshipsDTO censorshipsDTO) {
        getCensorshipById(id);
        Censorships updatedCensorship = censorshipsRepository.save(
                Censorships.builder()
                        .id(id)
                        .contentID(censorshipsDTO.getContentID())
                        .status(censorshipsDTO.getStatus())
                        .feedback(censorshipsDTO.getFeedback())
                        .build()
        );
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship updated successfully",
                        "censorship", updatedCensorship
                )
        );
    }

    @Override
    public ResponseEntity<Object> deleteCensorship(String id) {
        getCensorshipById(id);
        censorshipsRepository.deleteById(id);
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Censorship deleted successfully"
                )
        );
    }
}

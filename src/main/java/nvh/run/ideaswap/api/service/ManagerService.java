package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ManagerDTO;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.repository.ManagerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerService {
    ManagerRepository managerRepository;

    public ResponseEntity<Object> getAllManagers() {
        List<Managers> managers = managerRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "managers", managers));
    }

    public ResponseEntity<Object> getManagerById(String id) {
        Managers manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return ResponseEntity.ok(Map.of("success", true, "manager", manager));
    }

    public ResponseEntity<Object> createManager(ManagerDTO managerDTO) {
        Managers manager = managerRepository.save(
                Managers.builder()
                        .firstName(managerDTO.getFirstName())
                        .lastName(managerDTO.getLastName())
                        .username(managerDTO.getUsername())
                        .email(managerDTO.getEmail())
                        .phoneNumber(managerDTO.getPhoneNumber())
                        .build()
        );
        return ResponseEntity.status(201).body(Map.of("success", true, "manager", manager));
    }

    public ResponseEntity<Object> updateManager(String id, ManagerDTO managerDTO) {
        Managers existingManager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        existingManager.setFirstName(managerDTO.getFirstName());
        existingManager.setLastName(managerDTO.getLastName());
        managerRepository.save(existingManager);
        return ResponseEntity.ok(Map.of("success", true, "manager", existingManager));
    }

    public ResponseEntity<Object> deleteManager(String id) {
        managerRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Manager deleted successfully"));
    }
}


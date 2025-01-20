package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ManagerDTO;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerService {
    ManagerRepository managerRepository;

    public ManagerDTO getAllManagers() {
        List<Managers> managers = managerRepository.findAll();
        return ManagerDTO.builder().build();
    }

    public ManagerDTO getManagerById(String id) {
        Managers manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return ManagerDTO.builder().build();
    }

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Managers manager = managerRepository.save(
                Managers.builder()
                        .firstName(managerDTO.getFirstName())
                        .lastName(managerDTO.getLastName())
                        .username(managerDTO.getUsername())
                        .email(managerDTO.getEmail())
                        .phoneNumber(managerDTO.getPhoneNumber())
                        .build()
        );
        return ManagerDTO.builder().build();
    }

    public ManagerDTO updateManager(String id, ManagerDTO managerDTO) {
        Managers existingManager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        existingManager.setFirstName(managerDTO.getFirstName());
        existingManager.setLastName(managerDTO.getLastName());
        managerRepository.save(existingManager);
        return ManagerDTO.builder().build();
    }

    public ManagerDTO deleteManager(String id) {
        managerRepository.deleteById(id);
        return ManagerDTO.builder().build();
    }
}


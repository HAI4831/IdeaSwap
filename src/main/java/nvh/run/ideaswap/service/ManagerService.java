package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.repository.ManagerRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerService {
    ManagerRepository managerRepository;

    public List<Managers> getAllManagers() {
        List<Managers> managers ;
        try {
            managers = managerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all managers failed",e);
        }
        return managers;
    }

    public Managers getManagerById(ObjectId id) {
        Managers manager ;
        try {
            manager = managerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get manager failed",e);
        }
        return manager;
    }

    public Managers createManager(Managers manager) {
        try {
            manager = managerRepository.save(manager);
        } catch (Exception e) {
            throw new RuntimeException("Create manager failed",e);
        }
        return manager;
    }

    public Managers updateManager(ObjectId id, Managers manager) {
        getManagerById(id);
        manager.setId(id);
        try {
            manager = managerRepository.save(manager);
        } catch (Exception e) {
            throw new RuntimeException("Update manager failed",e);
        }
        return manager;
    }

    public Managers deleteManager(ObjectId id) {
        Managers manager = getManagerById(id);
        try {
            managerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete manager failed",e);
        }
        return manager;
    }
}


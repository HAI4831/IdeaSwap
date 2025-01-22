package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Censorships;
import nvh.run.ideaswap.data.repository.CensorshipsRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CensorshipsService {
    CensorshipsRepository censorshipsRepository;

    public List<Censorships> getAllCensorships() {
        List<Censorships> censorships;
        try {
            censorships = censorshipsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all censorships failed",e);
        }
        return censorships;
    }

    public Censorships getCensorshipById(ObjectId id) {
        Censorships censorship;
        try {
            censorship = censorshipsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Censorship not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get censorship failed",e);
        }
        return censorship;
    }

    public Censorships createCensorship(Censorships censorship) {
        try {
            censorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Create censorship failed",e);
        }
        return censorship;
    }

    public Censorships updateCensorship(ObjectId id, Censorships censorship) {
        getCensorshipById(id);
        Censorships updatedCensorship;
        censorship.setId(id);
        try {
            updatedCensorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Update censorship failed",e);
        }
        return updatedCensorship;
    }

    public Censorships deleteCensorship(ObjectId id) {
        Censorships censorships = getCensorshipById(id);
        try {
            censorshipsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete censorship failed",e);
        }
        return censorships;
    }
}

package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import org.springframework.http.ResponseEntity;

public interface ICensorships {
    ResponseEntity<Object> getAllCensorships();

    ResponseEntity<Object> getCensorshipById(String id);

    ResponseEntity<Object> createCensorship(CensorshipsDTO censorshipsDTO);

    ResponseEntity<Object> updateCensorship(String id, CensorshipsDTO censorshipsDTO);

    ResponseEntity<Object> deleteCensorship(String id);
}


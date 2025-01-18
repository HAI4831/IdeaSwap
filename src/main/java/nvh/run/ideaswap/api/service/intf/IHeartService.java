package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.HeartDTO;
import nvh.run.ideaswap.data.entity.Hearts;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IHeartService {
    ResponseEntity<Object> getAllHearts();  // Lấy tất cả các tim

    ResponseEntity<Object> getHeartsByUserID(String userID);  // Lấy tim của người dùng theo userID

    ResponseEntity<Object> createHeart(HeartDTO heartDTO);  // Tạo một tim mới

    ResponseEntity<Object> deleteHeart(String id);  // Xóa một tim theo ID

    ResponseEntity<Object> getHeartsByReferenceID(String referenceID);  // Lấy tim theo referenceID
}

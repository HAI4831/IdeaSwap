package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.FollowDTO;
import org.springframework.http.ResponseEntity;

public interface IFollowService {
    ResponseEntity<Object> getAllFollows();  // Lấy tất cả các follow

    ResponseEntity<Object> getFollowsByUserID(String userID);  // Lấy các follow theo userID

    ResponseEntity<Object> createFollow(FollowDTO followDTO);  // Tạo một follow mới

    ResponseEntity<Object> deleteFollow(String id);  // Xóa một follow theo ID

    ResponseEntity<Object> getFollowersByUserID(String userID);  // Lấy những người theo dõi của userID
}

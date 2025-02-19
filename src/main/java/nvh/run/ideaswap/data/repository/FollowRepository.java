package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotBlank;
import nvh.run.ideaswap.data.entity.Follows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends MongoRepository<Follows, String> {
    List<Follows> findByUserID(@NotBlank(message = "ID người dùng không được để trống") String userID);
//    List<Follows> findByUserID(@Param("user_id") String userID);
//    List<Follows> findByFollowerID(@Param("follower_id") String followerID);
//    List<Follows> findByUserID_IdAndFollowerID_Id(@Param("user_id") String userID, @Param("follower_id") String followerID);
//    List<Follows> findByFollowerID_Id(String followerID);
//    List<Follows> findByUserID_IdAndFollowerID_Id(String userID, String followerID);
}

package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotBlank;
import nvh.run.ideaswap.data.entity.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends MongoRepository<Follow, String> {
    List<Follow> findByUserID(@NotBlank(message = "ID người dùng không được để trống") String userID);

    Optional<Follow> findByFollowerIDAndUserID(@NotBlank(message = "ID người theo dõi không được để trống") String followerID, @NotBlank(message = "ID người dùng không được để trống") String userID);
//    List<Follows> findByUserID(@Param("user_id") String userID);
//    List<Follows> findByFollowerID(@Param("follower_id") String followerID);
//    List<Follows> findByUserID_IdAndFollowerID_Id(@Param("user_id") String userID, @Param("follower_id") String followerID);
//    List<Follows> findByFollowerID_Id(String followerID);
//    List<Follows> findByUserID_IdAndFollowerID_Id(String userID, String followerID);
}

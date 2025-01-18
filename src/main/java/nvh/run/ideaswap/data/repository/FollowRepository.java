package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Follows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends MongoRepository<Follows, String> {
    List<Follows> findByUserID_Id(String userID);
    List<Follows> findByFollowerID_Id(String followerID);
    List<Follows> findByUserID_IdAndFollowerID_Id(String userID, String followerID);
}

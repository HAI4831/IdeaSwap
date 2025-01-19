package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notifications, String> {
    List<Notifications> findByUserIDsContaining(String userId);
}


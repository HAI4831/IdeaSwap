package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIDsContaining(String userId);
}


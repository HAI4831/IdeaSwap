package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.NotificationDTO;
import org.springframework.http.ResponseEntity;

public interface INotification {
    ResponseEntity<Object> getAllNotifications();
    ResponseEntity<Object> getNotificationById(String id);
    ResponseEntity<Object> createNotification(NotificationDTO notificationDTO);
    ResponseEntity<Object> updateNotification(String id, NotificationDTO notificationDTO);
    ResponseEntity<Object> deleteNotification(String id);
}


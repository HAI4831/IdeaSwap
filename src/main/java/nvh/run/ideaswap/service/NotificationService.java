package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Notifications;
import nvh.run.ideaswap.data.repository.NotificationRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationService {
    NotificationRepository notificationRepository;

    public List<Notifications> getAllNotifications() {
        List<Notifications> notifications;
        try {
            notifications = notificationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all notifications failed",e);
        }
        return notifications;
    }

    public Notifications getNotificationById(ObjectId id) {
        Notifications notification;
        try {
            notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get notification failed",e);
        }
        return notification;
    }

    public Notifications createNotification(Notifications notification) {
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            throw new RuntimeException("Create notification failed",e);
        }
        return notification;
    }

    public Notifications updateNotification(ObjectId id, Notifications notification) {
        getNotificationById(id);
        notification.setId(id);
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            throw new RuntimeException("Update notification failed",e);
        }
        return notification;
    }

    public Notifications deleteNotification(ObjectId id) {
        Notifications notifications = getNotificationById(id);
        try {
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete notification failed",e);
        }
        return notifications;
    }
}

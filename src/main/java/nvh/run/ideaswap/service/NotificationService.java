package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Notifications;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.NotificationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationService {
    NotificationRepository notificationRepository;
    UserService userService;

//    @Cacheable(value = "messages",key = "'page:' + #page + ':size:' + #size")
    public Page<Notifications> getAllNotifications(int page, int size) {
        Page<Notifications> notifications;
        Pageable pageable = PageRequest.of(page, size);
        try {
            notifications = notificationRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all notifications failed",e);
        }
        return notifications;
    }
//    @Cacheable(value="notifications")
    public List<Notifications> getAllNotifications() {
        List<Notifications> notifications;
        try {
            notifications = notificationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all notifications failed",e);
        }
        return notifications;
    }

    @Cacheable(value="notification",key="#id",condition = "#id!=null")
    public Notifications getNotificationById(String id) {
        Notifications notification;
        try {
            notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get notification failed",e);
        }
        return notification;
    }

    @CachePut(value="notification",key="notificationRequest.id",condition = "#notificationRequest.id!=null")
    public Notifications createNotification(NotificationRequest notificationRequest) {
        List<Users> users = notificationRequest.getUserIDs().stream().map(userService::findById).toList();
        Notifications notification = Notifications.builder()
                .id(notificationRequest.getId())
                .userIDs(users.stream().map(Users::getId).toList())
                .description(notificationRequest.getDescription())
                .imageUrl(notificationRequest.getImageUrl())
                .isUnRead(notificationRequest.isUnRead())
                .actorID(notificationRequest.getActorID())
                .referenceType(notificationRequest.getReferenceType())
                .isModal(notificationRequest.isModal())
                .referenceID(notificationRequest.getReferenceID())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            throw new RuntimeException("Create notification failed",e);
        }
        return notification;
    }

    @CachePut(value="notification",key="#id",condition = "#id!=null")
    public Notifications updateNotification(String id, NotificationRequest notificationRequest) {
        getNotificationById(id);
        List<Users> users = notificationRequest.getUserIDs().stream().map(userService::findById).toList();
        Notifications notification = Notifications.builder()
                .userIDs(users.stream().map(Users::getId).toList())
                .description(notificationRequest.getDescription())
                .imageUrl(notificationRequest.getImageUrl())
                .isUnRead(notificationRequest.isUnRead())
                .actorID(notificationRequest.getActorID())
                .referenceType(notificationRequest.getReferenceType())
                .isModal(notificationRequest.isModal())
                .referenceID(notificationRequest.getReferenceID())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        notification.setId(id);
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            throw new RuntimeException("Update notification failed",e);
        }
        return notification;
    }

    @CacheEvict(value="notification",key="#id",condition = "#id!=null")
    public Notifications deleteNotification(String id) {
        Notifications notifications = getNotificationById(id);
        try {
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete notification failed",e);
        }
        return notifications;
    }

    @Cacheable(value="notification",key="#userId",condition = "#id!=null")
    public List<Notifications> getNotificationByUserId(String userId) {
        List<Notifications> notificationsList;
        try {
            notificationsList = notificationRepository.findByUserIDsContaining(userId);
        } catch (Exception e) {
            throw new RuntimeException("Get notification failed",e);
        }
        return notificationsList;
    }
}

package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Notification;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
public class NotificationService {
    NotificationRepository notificationRepository;
    UserService userService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, @Lazy UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

//    @Cacheable(value = "messages",key = "'page:' + #page + ':size:' + #size")
    public Page<Notification> getAllNotifications(int page, int size) {
        Page<Notification> notifications;
        Pageable pageable = PageRequest.of(page, size);
        try {
            notifications = notificationRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all notifications failed",e);
        }
        return notifications;
    }
//    @Cacheable(value="notifications")
    public List<Notification> getAllNotifications() {
        List<Notification> notifications;
        try {
            notifications = notificationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all notifications failed",e);
        }
        return notifications;
    }

    @Cacheable(value="notification",key="#id",condition = "#id!=null")
    public Notification getNotificationById(String id) {
        Notification notification;
        try {
            notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get notification failed",e);
        }
        return notification;
    }

    @CachePut(value="notification",key="notificationRequest.id",condition = "#notificationRequest.id!=null")
    public Notification createNotification(NotificationRequest notificationRequest) {
        List<User> users = notificationRequest.getUserIDs().stream().map(userService::findById).toList();
        Notification notification = Notification.builder()
                .id(notificationRequest.getId())
                .userIDs(users.stream().map(User::getId).toList())
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
    public Notification updateNotification(String id, NotificationRequest notificationRequest) {
        getNotificationById(id);
        List<User> users = notificationRequest.getUserIDs().stream().map(userService::findById).toList();
        Notification notification = Notification.builder()
                .userIDs(users.stream().map(User::getId).toList())
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
    public Notification deleteNotification(String id) {
        Notification notification = getNotificationById(id);
        try {
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete notification failed",e);
        }
        return notification;
    }

    @Cacheable(value="notification",key="#userId",condition = "#id!=null")
    public List<Notification> getNotificationsByUserId(String userId) {
        List<Notification> notificationList;
        try {
            notificationList = notificationRepository.findByUserIDsContaining(userId);
        } catch (Exception e) {
            throw new RuntimeException("Get notification failed",e);
        }
        return notificationList;
    }
}

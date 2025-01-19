package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.INotification;
import nvh.run.ideaswap.data.dto.NotificationDTO;
import nvh.run.ideaswap.data.entity.Notifications;
import nvh.run.ideaswap.data.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationService implements INotification {
    NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<Object> getAllNotifications() {
        List<Notifications> notifications;
        try {
            notifications = notificationRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("success", false, "message", "Retrieve List Notifications failed", "error", e.getMessage())
            );
        }
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve List Notifications successfully", "data", notifications)
        );
    }

    @Override
    public ResponseEntity<Object> getNotificationById(String id) {
        Notifications notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve Notification By ID successfully", "data", notification)
        );
    }

    @Override
    public ResponseEntity<Object> createNotification(NotificationDTO notificationDTO) {
        Notifications savedNotification = notificationRepository.save(
                Notifications.builder()
                        .description(notificationDTO.getDescription())
                        .imageUrl(notificationDTO.getImageUrl())
                        .isUnRead(notificationDTO.isUnRead())
                        .userIDs(notificationDTO.getUserIds())
                        .actorID(notificationDTO.getActorId())
                        .referenceType(notificationDTO.getReferenceType())
                        .referenceID(notificationDTO.getReferenceId())
                .build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Create Notification successfully", "data", savedNotification)
        );
    }

    @Override
    public ResponseEntity<Object> updateNotification(String id, NotificationDTO notificationDTO) {
        getNotificationById(id);
        Notifications updatedNotification = Notifications.builder()
                .description(notificationDTO.getDescription())
                .imageUrl(notificationDTO.getImageUrl())
                .isUnRead(notificationDTO.isUnRead())
                .userIDs(notificationDTO.getUserIds())
                .actorID(notificationDTO.getActorId())
                .referenceType(notificationDTO.getReferenceType())
                .referenceID(notificationDTO.getReferenceId())
                .build();
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Update Notification successfully", "data", updatedNotification)
        );
    }

    @Override
    public ResponseEntity<Object> deleteNotification(String id) {
        getNotificationById(id);
        notificationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Delete Notification successfully"));
    }
}


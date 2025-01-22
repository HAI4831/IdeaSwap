package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Notifications;
import nvh.run.ideaswap.service.NotificationService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Object> getAllNotifications() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve list of notifications successfully",
                        "data", notificationService.getAllNotifications())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNotificationById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve notification by ID successfully",
                        "notification", notificationService.getNotificationById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createNotification(@RequestBody Notifications notification) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create notification successfully",
                        "data", notificationService.createNotification(notification)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNotification(@PathVariable ObjectId id, @RequestBody Notifications notification) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Update notification successfully",
                        "notification", notificationService.updateNotification(id, notification)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Delete notification successfully",
                        "notification", notificationService.deleteNotification(id)
                )
        );
    }
}


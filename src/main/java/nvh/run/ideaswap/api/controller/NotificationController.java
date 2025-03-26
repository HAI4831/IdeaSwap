package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.service.NotificationService;
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

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve notification by ID successfully",
                        "notification", notificationService.getNotificationById(id)
                )
        );
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotificationByUserId(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve notification by ID successfully",
                        "notification", notificationService.getNotificationByUserId(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody NotificationRequest notificationRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create notification successfully",
                        "data", notificationService.createNotification(notificationRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateNotification(@PathVariable String id, @RequestBody NotificationRequest notificationRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Update notification successfully",
                        "notification", notificationService.updateNotification(id, notificationRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Delete notification successfully",
                        "notification", notificationService.deleteNotification(id)
                )
        );
    }
}


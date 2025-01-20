package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.NotificationDTO;
import nvh.run.ideaswap.data.entity.Notifications;

import java.util.stream.Collectors;

public class NotificationMapper {
    public static NotificationDTO toDto(Notifications notification) {
        if (notification == null) {
            return null;
        }
        return NotificationDTO.builder()
                .id(notification.getId())
                .description(notification.getDescription())
                .imageUrl(notification.getImageUrl())
                .isUnRead(notification.isUnRead())
                .userIds(notification.getUserIDs().stream()
                        .map(user -> user.getId())
                        .collect(Collectors.toList()))
                .actorId(notification.getActorID().toHexString())
                .referenceType(notification.getReferenceType())
                .isModal(notification.isModal())
                .referenceId(notification.getReferenceID().toHexString())
                .build();
    }

    public static Notifications toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        Notifications notification = Notifications.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .isUnRead(dto.isUnRead())
                .referenceType(dto.getReferenceType())
                .isModal(dto.isModal())
                .build();
        return notification;
    }
}


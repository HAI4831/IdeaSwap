package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.ConversationsDTO;

public class ConversationsMapper {
    public static ConversationsDTO toDTO(Conversations entity) {
        if (entity == null) {
            return null;
        }
        return ConversationsDTO.builder()
                .id(entity.getId())
                .members(entity.getMembers())
                .wallpaperUrl(entity.getWallpaperUrl())
                .build();
    }

    public static Conversations toEntity(ConversationsDTO dto) {
        if (dto == null) {
            return null;
        }
        return Conversations.builder()
                .id(dto.getId())
                .members(dto.getMembers())
                .wallpaperUrl(dto.getWallpaperUrl())
                .build();
    }
}


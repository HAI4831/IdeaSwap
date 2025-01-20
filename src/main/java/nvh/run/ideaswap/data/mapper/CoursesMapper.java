package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.CoursesDTO;

public class CoursesMapper {
    public static CoursesDTO toDTO(Courses entity) {
        if (entity == null) {
            return null;
        }
        return CoursesDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserID() != null ? entity.getUserID().getId() : null)
                .categoryId(entity.getCategoryID() != null ? entity.getCategoryID().getId() : null)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .view(entity.getView())
                .build();
    }

    public static Courses toEntity(CoursesDTO dto) {
        if (dto == null) {
            return null;
        }
        return Courses.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .view(dto.getView())
                .build();
    }
}


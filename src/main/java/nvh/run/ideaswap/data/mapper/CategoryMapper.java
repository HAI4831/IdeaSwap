package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.CategoryDTO;
import nvh.run.ideaswap.data.entity.Categories;

public class CategoryMapper {

    public CategoryDTO toDTO(Categories entity) {
        if (entity == null) {
            return null;
        }
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public Categories toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        return Categories.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}

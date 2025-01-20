package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.DocumentsDTO;
import nvh.run.ideaswap.data.entity.Documents;

public class DocumentsMapper {
    public static DocumentsDTO toDTO(Documents entity) {
        if (entity == null) {
            return null;
        }
        return DocumentsDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserID() != null ? entity.getUserID().getId() : null)
                .categoryId(entity.getCategoryID() != null ? entity.getCategoryID().getId() : null)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .fileUrl(entity.getFileUrl())
                .imageUrl(entity.getImageUrl())
                .countDownload(entity.getCountDownload())
                .build();
    }

    public static Documents toEntity(DocumentsDTO dto) {
        if (dto == null) {
            return null;
        }
        return Documents.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .fileUrl(dto.getFileUrl())
                .imageUrl(dto.getImageUrl())
                .countDownload(dto.getCountDownload())
                .build();
    }
}

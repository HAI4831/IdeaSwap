package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.CommentsDTO;
import nvh.run.ideaswap.data.entity.Comments;
import org.bson.types.ObjectId;

public class CommentsMapper {

    public static CommentsDTO toDTO(Comments entity) {
        if (entity == null) {
            return null;
        }
        return CommentsDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .parentCommentID(entity.getParentCommentID() != null ? entity.getParentCommentID().toHexString() : null)
                .userID(entity.getUserID() != null ? entity.getUserID().getId() : null)
                .referenceID(entity.getReferenceID() != null ? entity.getReferenceID().toString() : null)
                .build();
    }

    public static Comments toEntity(CommentsDTO dto) {
        if (dto == null) {
            return null;
        }
        return Comments.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .parentCommentID(dto.getParentCommentID() != null ? new ObjectId(dto.getParentCommentID()) : null)
                // userID mapping depends on how Users are handled
                .referenceID(dto.getReferenceID() != null ? new ObjectId(dto.getReferenceID()) : null)
                .build();
    }
}


package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.CensorshipsDTO;
import nvh.run.ideaswap.data.entity.Censorships;
import org.bson.types.ObjectId;

public class CensorshipsMapper {

    public static CensorshipsDTO toDTO(Censorships entity) {
        if (entity == null) {
            return null;
        }
        return CensorshipsDTO.builder()
                .id(entity.getId())
                .contentID(entity.getContentID() != null ? entity.getContentID().toHexString() : null)
                .status(entity.getStatus())
                .feedback(entity.getFeedback())
                .build();
    }

    public static Censorships toEntity(CensorshipsDTO dto) {
        if (dto == null) {
            return null;
        }
        return Censorships.builder()
                .id(dto.getId())
                .contentID(dto.getContentID() != null ? new ObjectId(dto.getContentID()) : null)
                .status(dto.getStatus())
                .feedback(dto.getFeedback())
                .build();
    }
}

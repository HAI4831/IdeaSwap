package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.ShareDto;
import nvh.run.ideaswap.data.entity.Shares;
import nvh.run.ideaswap.data.entity.Users;
import org.bson.types.ObjectId;

public class SharesMapper {
    public static ShareDto toDto(Shares shares) {
        if (shares == null) return null;
        return ShareDto.builder()
                .id(shares.getId())
                .userID(shares.getUserID() != null ? shares.getUserID().getId() : null)
                .referenceID(shares.getReferenceID() != null ? shares.getReferenceID().toHexString() : null)
                .build();
    }

    public static Shares toEntity(ShareDto shareDto, Users user) {
        if (shareDto == null) return null;
        return Shares.builder()
                .id(shareDto.getId())
                .userID(user)
                .referenceID(shareDto.getReferenceID() != null ? new ObjectId(shareDto.getReferenceID()) : null)
                .build();
    }
}


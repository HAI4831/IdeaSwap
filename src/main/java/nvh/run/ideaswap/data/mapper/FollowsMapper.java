package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.FollowDTO;
import nvh.run.ideaswap.data.entity.Follows;

public class FollowsMapper {

    public static FollowDTO toDTO(Follows follows) {
        if (follows == null) {
            return null;
        }
        return FollowDTO.builder()
                .id(follows.getId())
                .followerID(follows.getFollowerID() != null ? follows.getFollowerID().getId() : null)
                .userID(follows.getUserID() != null ? follows.getUserID().getId() : null)
                .build();
    }

    public static Follows toEntity(FollowDTO dto) {
        if (dto == null) {
            return null;
        }
        return Follows.builder()
                .id(dto.getId())
                .followerID(dto.getFollowerID() != null ? Follows.builder().id(dto.getFollowerID()).build() : null)
                .userID(dto.getUserID() != null ? Users.builder().id(dto.getUserID()).build() : null)
                .build();
    }
}


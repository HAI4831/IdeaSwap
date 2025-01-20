package nvh.run.ideaswap.data.mapper;

public class HeartsMapper {

    public static HeartDTO toDTO(Hearts hearts) {
        if (hearts == null) {
            return null;
        }
        return HeartDTO.builder()
                .id(hearts.getId())
                .userID(hearts.getUserID() != null ? hearts.getUserID().getId() : null)
                .referenceID(hearts.getReferenceID())
                .build();
    }

    public static Hearts toEntity(HeartDTO dto) {
        if (dto == null) {
            return null;
        }
        return Hearts.builder()
                .id(dto.getId())
                .userID(dto.getUserID() != null ? Users.builder().id(dto.getUserID()).build() : null)
                .referenceID(dto.getReferenceID())
                .build();
    }
}

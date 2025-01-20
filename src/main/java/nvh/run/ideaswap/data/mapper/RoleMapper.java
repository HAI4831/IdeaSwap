package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.RoleDTO;
import nvh.run.ideaswap.data.entity.Roles;

public class RoleMapper {
    public static RoleDTO toDto(Roles role) {
        if (role == null) {
            return null;
        }
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Roles toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        return Roles.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}


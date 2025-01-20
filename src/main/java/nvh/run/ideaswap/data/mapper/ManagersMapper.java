package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.ManagerDTO;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.entity.Roles;
import org.springframework.stereotype.Component;

@Component
public class ManagersMapper {

    public Managers toEntity(ManagerDTO managerDTO,Roles role) {
        if (managerDTO == null) {
            return null;
        }
        return Managers.builder()
                .id(managerDTO.getId())
                .firstName(managerDTO.getFirstName())
                .lastName(managerDTO.getLastName())
                .username(managerDTO.getUsername())
                .email(managerDTO.getEmail())
                .phoneNumber(managerDTO.getPhoneNumber())
                .address(managerDTO.getAddress())
                .avatar(managerDTO.getAvatar())
                .birthday(managerDTO.getBirthday())
                .gender(managerDTO.getGender())
                .roleID(role)  // Assuming `Roles` has a constructor that accepts String
                .build();
    }

    public ManagerDTO toDTO(Managers managers) {
        if (managers == null) {
            return null;
        }
        return ManagerDTO.builder()
                .id(managers.getId())
                .firstName(managers.getFirstName())
                .lastName(managers.getLastName())
                .username(managers.getUsername())
                .email(managers.getEmail())
                .phoneNumber(managers.getPhoneNumber())
                .address(managers.getAddress())
                .avatar(managers.getAvatar())
                .birthday(managers.getBirthday())
                .gender(managers.getGender())
                .roleId(managers.getRoleID() != null ? managers.getRoleID().getId() : null)  // Assuming `Roles` has a getId() method
                .build();
    }
}


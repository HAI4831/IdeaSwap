package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.UserDTO;
import nvh.run.ideaswap.data.entity.Users;

public class UsersMapper {
    public static UserDTO toDto(Users users) {
        if (users == null) return null;
        return UserDTO.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .username(users.getUsername())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .address(users.getAddress())
                .avatar(users.getAvatar())
                .gender(users.getGender())
                .rating(users.getRating())
                .description(users.getDescription())
                .birthday(users.getBirthday())
                .build();
    }

    public static Users toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        return Users.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .avatar(userDTO.getAvatar())
                .gender(userDTO.getGender())
                .rating(userDTO.getRating())
                .description(userDTO.getDescription())
                .birthday(userDTO.getBirthday())
                .build();
    }
}


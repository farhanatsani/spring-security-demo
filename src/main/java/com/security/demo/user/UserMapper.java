package com.security.demo.user;

public class UserMapper {
    public static User toUser(RegistrationUserRequestDTO userRequestDTO) {
        User user = User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
        return user;
    }
    public static RegistrationUserResponseDTO toRegistrationUserResponseDTO(User user) {
        RegistrationUserResponseDTO responseDTO = RegistrationUserResponseDTO.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getLastName())
                .username(user.getUsername())
                .build();
        return responseDTO;
    }
}

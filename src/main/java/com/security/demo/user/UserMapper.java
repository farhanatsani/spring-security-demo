package com.security.demo.user;

import com.security.demo.base.util.PasswordEncoderUtil;

import java.util.Set;

public class UserMapper {
    public static User toUser(RegistrationUserRequestDTO userRequestDTO) {
        User user = User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(PasswordEncoderUtil.passwordEncoder().encode(userRequestDTO.getPassword()))
                .authorities(Set.of(
                        GrantedAuthorityImpl.builder().authority("USER_WEB").build(),
                        GrantedAuthorityImpl.builder().authority("USER_MOBILE").build()
                ))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
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
    public static UserProfileDTO toUserProfileDTO(User user) {
        return UserProfileDTO.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getLastName())
                .username(user.getUsername())
                .authorities(user.getAuthorities())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
    }
}

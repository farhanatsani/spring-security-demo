package com.security.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class RegistrationUserResponseDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}

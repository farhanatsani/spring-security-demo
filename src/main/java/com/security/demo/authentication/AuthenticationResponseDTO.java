package com.security.demo.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AuthenticationResponseDTO {
    private String username;
    private String token;
    private long expiration;
}

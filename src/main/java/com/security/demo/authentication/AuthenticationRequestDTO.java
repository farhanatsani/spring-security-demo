package com.security.demo.authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}

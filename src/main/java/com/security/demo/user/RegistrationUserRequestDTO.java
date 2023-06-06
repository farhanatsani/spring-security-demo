package com.security.demo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationUserRequestDTO {
    @NotNull(message = "username cannot be null")
    private String username;
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "firstName cannot be null")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotNull(message = "password cannot be null")
    private String password;
}

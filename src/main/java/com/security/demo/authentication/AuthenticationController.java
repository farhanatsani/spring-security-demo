package com.security.demo.authentication;

import com.security.demo.base.ResponseMessage;
import com.security.demo.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationProvider authenticationProviderImpl;
    private JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDTO login) {
        Authentication authentication = authenticationProviderImpl.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword())
        );
        if(!authentication.isAuthenticated()) {
            throw new AuthenticationServiceException(ResponseMessage.AUTH_IS_INVALID);
        }
        String jwt = jwtUtils.generateJwtToken(authentication);
        int expirationInSecond = (int) TimeUnit.MILLISECONDS.toSeconds(3600000L);
        AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder()
                .username(login.getUsername())
                .token(jwt)
                .expiration(expirationInSecond)
                .build();
        return ResponseEntity.ok(authResponse);
    }
}

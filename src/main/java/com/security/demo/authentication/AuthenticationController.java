package com.security.demo.authentication;

import com.security.demo.base.ResponseMessage;
import com.security.demo.security.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationProvider authenticationProviderImpl;
    private RedisTemplate redisTemplate;
    private JwtUtils jwtUtils;
    @PostMapping("/login")
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
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        log.info("token {}", token);

        String md5Hex = DigestUtils.md5Hex(token).toUpperCase();
        Date date = jwtUtils.getExpirationFromJwtToken(token);

        redisTemplate.opsForValue()
                .set(md5Hex, date, Duration.ofSeconds(3600000L));

        return ResponseEntity.ok("Logout sukses");
    }
}

package com.security.demo.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author farhan
 */
@Component
@Slf4j
public class JwtUtils {
    private Date currentDate = new Date();
    private long jwtExpiration = 3600000L;
    @Value("${jwtSecret}")
    private String jwtSecret;
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setAudience(userPrincipal.getUsername())
                .setIssuer("Spring security demo")
                .setSubject("Demo jwt")
                .setIssuedAt(currentDate)
                .setExpiration(new Date((currentDate).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, Base64Utils.encode(jwtSecret.getBytes()))
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64Utils.encode(jwtSecret.getBytes()))
                    .parseClaimsJws(token)
                    .getBody().getAudience();
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return "";
    }
    public Date getExpirationFromJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64Utils.encode(jwtSecret.getBytes()))
                    .parseClaimsJws(token)
                    .getBody().getExpiration();
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return Date.from(LocalDate.now()
                .minusDays(7)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }
}

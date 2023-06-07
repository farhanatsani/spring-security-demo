package com.security.demo.security;

import com.security.demo.base.ResponseMessage;
import com.security.demo.base.util.PasswordEncoderUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private UserDetailsService userDetailServiceImpl;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(authentication.getName());
        boolean passwordMatches = PasswordEncoderUtil.passwordEncoder()
                .matches(authentication.getCredentials().toString(),
                        userDetails.getPassword());
        if(!passwordMatches) {
            throw new BadCredentialsException(ResponseMessage.AUTH_IS_INVALID);
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials().toString(),
                userDetails.getAuthorities()
        );
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

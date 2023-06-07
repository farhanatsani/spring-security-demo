package com.security.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter @Setter @Builder
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;
}

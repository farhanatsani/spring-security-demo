package com.security.demo.user;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameOrPassword(String userParam);
    User addAuthority(String userParam, String authority);
}

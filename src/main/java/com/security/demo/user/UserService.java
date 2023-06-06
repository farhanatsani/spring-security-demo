package com.security.demo.user;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
}

package com.security.demo.user.impl;

import com.security.demo.base.ResponseMessage;
import com.security.demo.user.User;
import com.security.demo.user.UserRepository;
import com.security.demo.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User save(User user) {
        Optional<User> checkUsernameOptional = userRepository.findByUsername(user.getUsername());
        Optional<User> checkEmailOptional = userRepository.findByEmail(user.getEmail());
        if(checkUsernameOptional.isPresent()) {
            throw new EntityExistsException(ResponseMessage.USERNAME_ALREADY_USED);
        }
        if(checkEmailOptional.isPresent()) {
            throw new EntityExistsException(ResponseMessage.EMAIL_ALREADY_USED);
        }
        return userRepository.save(user);
    }
    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()) {
            throw new NullPointerException(ResponseMessage.USER_NOT_FOUND);
        }
        return userOptional.get();
    }
    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new NullPointerException(ResponseMessage.USER_NOT_FOUND);
        }
        return userOptional.get();
    }
    @Override
    public User loginByUsernameOrPassword(String userParam) {
        Optional<User> userLoginOptional = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(userParam, userParam);
        if(userLoginOptional.isEmpty()) {
            throw new UsernameNotFoundException(ResponseMessage.USER_NOT_FOUND + ": " + userParam);
        }
        return userLoginOptional.get();
    }
}

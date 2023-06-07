package com.security.demo.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userServiceImpl;
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userServiceImpl.findByUsername(username);
        UserProfileDTO userProfile = UserMapper.toUserProfileDTO(user);
        return ResponseEntity.ok(userProfile);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registrationUser(@RequestBody @Valid RegistrationUserRequestDTO registrationUserRequestDTO) {
        User user = UserMapper.toUser(registrationUserRequestDTO);
        User userSave = userServiceImpl.save(user);
        RegistrationUserResponseDTO responseDTO = UserMapper.toRegistrationUserResponseDTO(userSave);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDTO);
    }
}

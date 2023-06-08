package com.security.demo;

import com.security.demo.base.util.PasswordEncoderUtil;
import com.security.demo.user.GrantedAuthorityImpl;
import com.security.demo.user.User;
import com.security.demo.user.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class SecurityDemoApplication implements ApplicationRunner {
	private final UserRepository userRepository;
	public SecurityDemoApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = User.builder()
				.firstName("John")
				.lastName("Doe")
				.username("john")
				.email("john.doe@email.com")
				.password(PasswordEncoderUtil.passwordEncoder().encode("password"))
				.authorities(Set.of(
						GrantedAuthorityImpl.builder().authority("USER_WEB").build(),
						GrantedAuthorityImpl.builder().authority("USER_ADMIN").build()
				))
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
		userRepository.save(user);
	}
}

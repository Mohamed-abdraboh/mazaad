package com.global.mazaad.common.config;

import com.global.mazaad.user.entity.Role;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInit implements CommandLineRunner {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    User user =
        User.builder()
            .name("mohamed")
            .role(Role.ROLE_ADMIN)
            .isAccountNonLocked(true)
            .isEnabled(true)
            .password(passwordEncoder.encode("12345678"))
            .phoneNumber("01015422609")
            .build();
    userRepository.save(user);
  }
}

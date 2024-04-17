package com.global.mazaad.security.service;

import com.global.mazaad.security.converter.UserToRegistrationRequestMapper;
import com.global.mazaad.security.dto.UserAuthenticationResponse;
import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.security.jwt.JwtTokenProvider;
import com.global.mazaad.user.entity.Role;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService {

  private static final boolean DEFAULT_ACCOUNT_NON_EXPIRED = true;
  private static final boolean DEFAULT_ACCOUNT_NON_LOCKED = true;
  private static final boolean DEFAULT_CREDENTIALS_NON_EXPIRED = true;
  private static final boolean DEFAULT_ENABLED = true;

  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final UserToRegistrationRequestMapper userToRegistrationRequestMapper;
  private final PasswordEncoder passwordEncoder;

  public UserAuthenticationResponse register(
      final UserRegistrationRequest userRegistrationRequest) {
    String encryptedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());

    User user = userToRegistrationRequestMapper.mapToUser(userRegistrationRequest);
    user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
    user.setRole(Role.ROLE_USER);
    user.setEnabled(DEFAULT_ENABLED);
    user.setAccountNonLocked(DEFAULT_ACCOUNT_NON_LOCKED);

    userRepository.save(user);

    final String jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user);
    final String jwtToken = jwtTokenProvider.generateToken(user);

    return new UserAuthenticationResponse(jwtToken, jwtRefreshToken);
  }
}

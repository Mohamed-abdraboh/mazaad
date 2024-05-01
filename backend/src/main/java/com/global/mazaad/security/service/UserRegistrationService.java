package com.global.mazaad.security.service;

import com.global.mazaad.security.converter.UserToRegistrationRequestMapper;
import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.security.jwt.JwtTokenProvider;
import com.global.mazaad.user.entity.Role;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.repository.UserRepository;
import com.global.mazaad.user.service.UserService;
import com.global.mazaad.user.service.UserValidator;
import com.global.mazaad.whatsapp.service.WhatsappManager;
import com.global.mazaad.whatsapp.service.WhatsappOtpSender;
import com.global.mazaad.whatsapp.token.TokenGenerator;
import com.global.mazaad.whatsapp.token.service.TokenService;
import com.global.mazaad.whatsapp.token.service.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService {

  private static final boolean DEFAULT_ACCOUNT_NON_LOCKED = true;
  private static final boolean DEFAULT_ENABLED = false;

  private final UserRepository userRepository;
  private final UserToRegistrationRequestMapper userToRegistrationRequestMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final WhatsappManager whatsappManager;
  private final UserValidator userValidator;
  private final TokenValidator tokenValidator;

  public void register(final UserRegistrationRequest userRegistrationRequest) {
    String encryptedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());

    User user = userToRegistrationRequestMapper.mapToUser(userRegistrationRequest);
    user.setPassword(encryptedPassword);
    user.setRole(Role.ROLE_USER);
    user.setEnabled(DEFAULT_ENABLED);
    user.setAccountNonLocked(DEFAULT_ACCOUNT_NON_LOCKED);

    userValidator.validateIfPresent(userRegistrationRequest.getPhoneNumber());
    userRepository.save(user);
    sendOtp(userRegistrationRequest);
  }

  private void sendOtp(UserRegistrationRequest userRegistrationRequest) {
    String phoneNumber = userRegistrationRequest.getPhoneNumber();
    whatsappManager.sendOtp(phoneNumber);
  }

  public void activate(String phoneNumber, String token) {
    User user = userService.findByPhoneNumber(phoneNumber);
    tokenValidator.validate(token, user);

    user.setEnabled(true);
    userRepository.save(user);
  }
}

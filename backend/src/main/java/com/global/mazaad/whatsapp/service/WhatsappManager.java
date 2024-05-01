package com.global.mazaad.whatsapp.service;

import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.repository.UserRepository;
import com.global.mazaad.user.service.UserService;
import com.global.mazaad.whatsapp.token.TokenGenerator;
import com.global.mazaad.whatsapp.token.entity.Token;
import com.global.mazaad.whatsapp.token.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhatsappManager {
  private final WhatsappOtpSender whatsappOtpSender;
  private final TokenGenerator tokenGenerator;
  private final UserService userService;
  private final TokenService tokenService;

  @Transactional
  public void sendOtp(String destinationNumber) {
    User user = userService.findByPhoneNumber(destinationNumber);
    String token = tokenGenerator.generateToken();
    tokenService.save(token, user);
    whatsappOtpSender.sendOtp(destinationNumber, token);
  }
}

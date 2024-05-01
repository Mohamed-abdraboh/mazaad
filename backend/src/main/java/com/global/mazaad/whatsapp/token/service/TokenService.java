package com.global.mazaad.whatsapp.token.service;

import com.global.mazaad.user.entity.User;
import com.global.mazaad.whatsapp.exception.TokenNotFoundException;
import com.global.mazaad.whatsapp.token.entity.Token;
import com.global.mazaad.whatsapp.token.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final TokenRepository tokenRepository;

  @Transactional
  public void save(String tokenValue, User user) {
    Token token = tokenRepository.findByUser(user).orElse(new Token());
    token.setUser(user);
    token.setValue(tokenValue);
    token.setExpiryDateTime(LocalDateTime.now().plusMinutes(10));
    tokenRepository.save(token);
  }

  public Token getByTokenValue(String tokenValue) {
    return tokenRepository.findByValue(tokenValue).orElseThrow(TokenNotFoundException::new);
  }
}

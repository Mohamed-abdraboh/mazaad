package com.global.mazaad.whatsapp.token.service;

import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.service.UserValidator;
import com.global.mazaad.whatsapp.exception.TokenExpiredException;
import com.global.mazaad.whatsapp.exception.TokenNotFoundException;
import com.global.mazaad.whatsapp.exception.TokenNotSentException;
import com.global.mazaad.whatsapp.token.entity.Token;
import com.global.mazaad.whatsapp.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenValidator {
  private final TokenService tokenService;

  public void validate(String tokenValue, User user) {
    Token token = tokenService.getByTokenValue(tokenValue);

    validateIfUserToken(token, user);
    validateIfExpired(token);
  }

  private void validateIfUserToken(Token token, User user) {
    if (!user.equals(token.getUser()))
      throw new TokenNotSentException("Token not valid for the provided user");
  }

  private void validateIfExpired(Token token) {
    if (token.getExpiryDateTime().isBefore(LocalDateTime.now())) throw new TokenExpiredException();
  }
}

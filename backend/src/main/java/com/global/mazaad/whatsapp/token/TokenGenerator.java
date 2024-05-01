package com.global.mazaad.whatsapp.token;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TokenGenerator {

  private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int TOKEN_LENGTH = 6;

  private final SecureRandom secureRandom = new SecureRandom();

  public String generateToken() {
    StringBuilder tokenBuilder = new StringBuilder();
    for (int i = 0; i < TOKEN_LENGTH; i++) {
      int randomIndex = secureRandom.nextInt(TOKEN_CHARS.length());
      tokenBuilder.append(TOKEN_CHARS.charAt(randomIndex));
    }
    return tokenBuilder.toString();
  }
}

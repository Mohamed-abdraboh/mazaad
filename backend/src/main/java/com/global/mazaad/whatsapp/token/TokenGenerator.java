package com.global.mazaad.whatsapp.token;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class TokenGenerator {
  private final SecureRandom secureRandom = new SecureRandom();
  private final int TOKEN_LENGTH = 6;

  public int generateToken() {
    return secureRandom.nextInt(TOKEN_LENGTH);
  }
}

package com.global.mazaad.whatsapp.exception;

public class TokenNotFoundException extends RuntimeException {
  public TokenNotFoundException() {
    super("Code Not Found");
  }
}

package com.global.mazaad.whatsapp.exception;

public class TokenNotSentException extends RuntimeException {
  public TokenNotSentException() {
    super("Token not sent.");
  }
}

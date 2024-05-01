package com.global.mazaad.whatsapp.exception;

public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException() {
    super("The provided Code has been expired");
  }
}

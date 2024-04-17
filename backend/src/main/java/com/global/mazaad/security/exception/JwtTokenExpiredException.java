package com.global.mazaad.security.exception;

import javax.security.sasl.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {
  public JwtTokenExpiredException() {
    super("JWT token is expired");
  }
}

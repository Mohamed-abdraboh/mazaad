package com.global.mazaad.security.exception.handler;

import com.global.mazaad.security.exception.JwtTokenException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtTokenExceptionsHandler {

  @ExceptionHandler(JwtTokenException.class)
  public ResponseEntity<Map<String, String>> handleJwtTokenException(
      final JwtTokenException exception) {
    Map<String, String> errors = new HashMap<>();
    errors.put("JwtToken Error message", exception.getMessage());
    errors.put("JwtToken Cause Error message", exception.getCause().getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
  }
}

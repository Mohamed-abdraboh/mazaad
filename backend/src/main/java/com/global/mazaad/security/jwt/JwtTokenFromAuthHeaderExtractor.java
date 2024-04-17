package com.global.mazaad.security.jwt;

import com.global.mazaad.security.exception.AbsentBearerHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenFromAuthHeaderExtractor {

  public static final int BEARER_PREFIX_LENGTH = 7;
  public static final String BEARER_PREFIX = "Bearer ";

  @Value("Authorization")
  private String jwtHttpRequestHeader;

  public String extract(final HttpServletRequest request) {
    String header = request.getHeader(jwtHttpRequestHeader);
    return extract(header);
  }

  public String extract(final String header) {
    return Optional.ofNullable(header)
        .filter(authHeader -> authHeader.startsWith(BEARER_PREFIX))
        .map(authHeader -> authHeader.substring(BEARER_PREFIX_LENGTH))
        .orElseThrow(AbsentBearerHeaderException::new);
  }
}

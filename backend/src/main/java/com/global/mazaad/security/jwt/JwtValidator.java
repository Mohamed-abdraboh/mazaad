package com.global.mazaad.security.jwt;

import com.global.mazaad.security.config.TokenType;
import com.global.mazaad.security.exception.JwtTokenBlacklistedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidator {
  private final JwtBlacklist jwtBlacklist;
  private final JwtClaimExtractor jwtClaimExtractor;

  public void validate(String jwtToken) {
    validateIfBlacklisted(jwtToken);
  }

  public void validateIfBlacklisted(String jwtToken) {
    if (jwtBlacklist.contains(jwtToken)) {
      throw new JwtTokenBlacklistedException();
    }
  }

  public boolean isRefresh(String jwtToken) {
    return jwtClaimExtractor.extractType(jwtToken).equals(TokenType.REFRESH.toString());
  }
}

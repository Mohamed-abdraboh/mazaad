package com.global.mazaad.security.jwt;

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
    //    validateIfExpired(jwtToken);
  }

  public void validateIfBlacklisted(String jwtToken) {
    if (jwtBlacklist.contains(jwtToken)) {
      throw new JwtTokenBlacklistedException();
    }
  }

  //  public void validateIfExpired(String jwtToken) {
  //    Date tokenExpiryDate = jwtClaimExtractor.extractExpirationDate(jwtToken);
  //    boolean isExpired = tokenExpiryDate.before(new Date(System.currentTimeMillis()));
  //    if (isExpired) throw new ExpiredJwtException();
  //  }
}

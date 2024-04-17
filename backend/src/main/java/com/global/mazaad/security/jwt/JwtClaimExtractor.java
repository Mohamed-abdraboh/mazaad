package com.global.mazaad.security.jwt;

import com.global.mazaad.security.exception.JwtTokenHasNoUserEmailException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.util.StringUtils;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtClaimExtractor {
  private final JwtSignKeyProvider jwtSignKeyProvider;

  public String extractPhoneNumer(final String jwtToken) {

    return extractAllClaims(jwtToken).getSubject();
  }

  public String extractEmail(final String jwtToken) {
    String userEmail = extractAllClaims(jwtToken).getSubject();

    if (StringUtils.isEmpty(userEmail)) throw new JwtTokenHasNoUserEmailException();
    else return userEmail;
  }

  public Date extractExpirationDate(final String jwtToken) {
    return extractAllClaims(jwtToken).getExpiration();
  }

  private Claims extractAllClaims(final String jwtToken) {
    return getJwtParser().parseSignedClaims(jwtToken).getPayload();
  }

  private JwtParser getJwtParser() {
    return Jwts.parser().verifyWith((SecretKey) jwtSignKeyProvider.get()).build();
  }
}

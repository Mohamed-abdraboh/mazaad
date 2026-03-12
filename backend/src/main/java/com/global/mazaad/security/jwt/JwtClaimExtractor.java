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

  // -----------------------------------------------------------------------
  // Access-token specific methods — validated against the ACCESS key
  // -----------------------------------------------------------------------

  public String extractPhoneNumber(final String jwtToken) {
    return extractAllClaims(jwtToken).getSubject();
  }

  public String extractType(final String jwtToken) {
    return extractAllClaims(jwtToken).get("type", String.class);
  }

  public String extractEmail(final String jwtToken) {
    String userEmail = extractAllClaims(jwtToken).getSubject();
    if (StringUtils.isEmpty(userEmail))
      throw new JwtTokenHasNoUserEmailException();
    return userEmail;
  }

  public Date extractExpirationDate(final String jwtToken) {
    return extractAllClaims(jwtToken).getExpiration();
  }

  // -----------------------------------------------------------------------
  // Refresh-token specific methods — validated against the REFRESH key
  // Using a separate key ensures a stolen refresh token cannot be submitted
  // as an access token (it would fail signature verification immediately).
  // -----------------------------------------------------------------------

  public String extractPhoneNumberFromRefreshToken(final String jwtToken) {
    String subject = extractAllClaimsFromRefreshToken(jwtToken).getSubject();
    if (StringUtils.isEmpty(subject))
      throw new JwtTokenHasNoUserEmailException();
    return subject;
  }

  public String extractTypeFromRefreshToken(final String jwtToken) {
    return extractAllClaimsFromRefreshToken(jwtToken).get("type", String.class);
  }

  // -----------------------------------------------------------------------
  // Private helpers
  // -----------------------------------------------------------------------

  private Claims extractAllClaims(final String jwtToken) {
    return getAccessJwtParser().parseSignedClaims(jwtToken).getPayload();
  }

  private Claims extractAllClaimsFromRefreshToken(final String jwtToken) {
    return getRefreshJwtParser().parseSignedClaims(jwtToken).getPayload();
  }

  private JwtParser getAccessJwtParser() {
    return Jwts.parser().verifyWith((SecretKey) jwtSignKeyProvider.get()).build();
  }

  private JwtParser getRefreshJwtParser() {
    return Jwts.parser().verifyWith((SecretKey) jwtSignKeyProvider.getRefresh()).build();
  }
}

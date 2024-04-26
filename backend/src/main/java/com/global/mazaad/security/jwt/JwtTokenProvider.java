package com.global.mazaad.security.jwt;

import com.global.mazaad.security.exception.JwtTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
  private final JwtSignKeyProvider jwtSignKeyProvider;

  @Value("${application.security.jwt.access-token.expiration-time}")
  private long validityInMilliseconds;

  @Value("${application.security.jwt.refresh-token.expiration-time}")
  private long validityRefreshTokenInMilliseconds;

  public String generateToken(final UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(
      final Map<String, Object> extraClaims, final UserDetails userDetails) {
    try {
      return Jwts.builder()
          .claims(extraClaims)
          .id(UUID.randomUUID().toString())
          .claim("type", "ACCESS")
          .subject(userDetails.getUsername())
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
          .signWith(jwtSignKeyProvider.get(), SignatureAlgorithm.HS256)
          .compact();
    } catch (Exception exception) {
      log.debug("Jwt accessToken creation error", exception);
      throw new JwtTokenException(exception);
    }
  }

  public String generateRefreshToken(final UserDetails userDetails) {
    try {
      return Jwts.builder()
          .subject(userDetails.getUsername())
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis() + validityRefreshTokenInMilliseconds))
          .id(UUID.randomUUID().toString())
          .claim("type", "REFRESH")
          .signWith(jwtSignKeyProvider.get(), SignatureAlgorithm.HS256)
          .compact();
    } catch (Exception exception) {
      log.debug("Jwt refresh token creation error", exception);
      throw new JwtTokenException(exception);
    }
  }
}

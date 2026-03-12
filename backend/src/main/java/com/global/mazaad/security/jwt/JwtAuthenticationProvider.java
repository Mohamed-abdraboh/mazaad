package com.global.mazaad.security.jwt;

import com.global.mazaad.common.exception.NotActivatedAccountException;
import com.global.mazaad.security.exception.JwtTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

  private final JwtTokenFromAuthHeaderExtractor jwtTokenFromAuthHeaderExtractor;
  private final JwtClaimExtractor jwtClaimExtractor;
  private final UserDetailsService userDetailsService;
  private final JwtValidator jwtValidator;

  public UsernamePasswordAuthenticationToken get(final HttpServletRequest httpRequest) {
    String jwtToken = jwtTokenFromAuthHeaderExtractor.extract(httpRequest);

    jwtValidator.validate(jwtToken);
    boolean isRefreshEndpoint = httpRequest.getServletPath().equals("/api/v1/auth/refresh");

    // Determine token type using the correct key for the endpoint.
    // On the refresh endpoint the token must be a REFRESH token (verified with
    // refresh key).
    // On all other secured endpoints the token must be an ACCESS token (verified
    // with access key).
    String jwtType;
    if (isRefreshEndpoint) {
      jwtType = jwtClaimExtractor.extractTypeFromRefreshToken(jwtToken);
    } else {
      jwtType = jwtClaimExtractor.extractType(jwtToken);
    }

    if (jwtType.equals("REFRESH") && !isRefreshEndpoint) {
      throw new JwtTokenException("can't access resources with refresh token");
    }

    final String phoneNumber = isRefreshEndpoint
        ? jwtClaimExtractor.extractPhoneNumberFromRefreshToken(jwtToken)
        : jwtClaimExtractor.extractPhoneNumber(jwtToken);

    UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNumber);

    if (!userDetails.isEnabled())
      throw new NotActivatedAccountException(userDetails.getUsername());
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

    return authenticationToken;
  }
}

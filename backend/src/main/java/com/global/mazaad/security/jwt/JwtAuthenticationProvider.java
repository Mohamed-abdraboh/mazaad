package com.global.mazaad.security.jwt;

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

    final String phoneNumber = jwtClaimExtractor.extractPhoneNumer(jwtToken);

    UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNumber);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

    return authenticationToken;
  }
}

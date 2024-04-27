package com.global.mazaad.security.endpoint;

import com.global.mazaad.security.dto.UserAuthenticationRequest;
import com.global.mazaad.security.dto.UserAuthenticationResponse;
import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.security.exception.JwtTokenException;
import com.global.mazaad.security.jwt.JwtAuthenticationProvider;
import com.global.mazaad.security.jwt.JwtBlacklist;
import com.global.mazaad.security.jwt.JwtTokenFromAuthHeaderExtractor;
import com.global.mazaad.security.jwt.JwtValidator;
import com.global.mazaad.security.repository.BlockedTokenRepository;
import com.global.mazaad.security.service.UserAuthenticationService;
import com.global.mazaad.security.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = UserSecurityEndpoint.USER_SECURITY_API_URL)
@RequiredArgsConstructor
public class UserSecurityEndpoint {

  public static final String USER_SECURITY_API_URL = "/api/v1/auth/";

  private final UserAuthenticationService userAuthenticationService;
  private final JwtTokenFromAuthHeaderExtractor jwtTokenFromAuthHeaderExtractor;
  private final JwtValidator jwtValidator;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;
  private final UserDetailsService userDetailsService;
  private final JwtBlacklist jwtBlacklist;

  private final HttpServletRequest httpRequest;

  private final UserRegistrationService userRegistrationService;

  @PostMapping("/register")
  public ResponseEntity<UserAuthenticationResponse> register(
      @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
    UserAuthenticationResponse userRegistrationResponse =
        userRegistrationService.register(userRegistrationRequest);
    return ResponseEntity.ok(userRegistrationResponse);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthenticationResponse> authenticate(
      @Valid @RequestBody final UserAuthenticationRequest request) {

    UserAuthenticationResponse authenticationResponse =
        userAuthenticationService.authenticate(request);

    return ResponseEntity.ok(authenticationResponse);
  }

  @PostMapping("/refresh")
  public ResponseEntity<UserAuthenticationResponse> refreshToken() {
    log.info("Received refresh accessToken request for user");
    UsernamePasswordAuthenticationToken authenticationToken =
        jwtAuthenticationProvider.get(httpRequest);
    UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationToken.getName());
    UserAuthenticationResponse authenticationResponse =
        userAuthenticationService.authenticate(userDetails, authenticationToken.getName());
    String token =
        jwtTokenFromAuthHeaderExtractor.extract(httpRequest.getHeader(HttpHeaders.AUTHORIZATION));
    if (!jwtValidator.isRefresh(token))
      throw new JwtTokenException("Can't refresh with access accessToken");
      jwtBlacklist.addToBlacklist(token);
    log.info("Refresh completed for user");
    return ResponseEntity.ok(authenticationResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    log.info("Received logout request");

    String token = jwtTokenFromAuthHeaderExtractor.extract(httpRequest);

    jwtBlacklist.addToBlacklist(token);

    return ResponseEntity.ok().build();
  }
  @PostMapping("/change")
  public ResponseEntity<Void> changePassword() {
    log.info("Received logout request");

    String token = jwtTokenFromAuthHeaderExtractor.extract(httpRequest);

    jwtBlacklist.addToBlacklist(token);

    return ResponseEntity
            .ok()
            .build();
  }
}

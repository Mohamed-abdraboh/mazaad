package com.global.mazaad.security.endpoint;

import com.global.mazaad.security.dto.UserAuthenticationRequest;
import com.global.mazaad.security.dto.UserAuthenticationResponse;
import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.security.exception.JwtTokenException;
import com.global.mazaad.security.jwt.JwtAuthenticationProvider;
import com.global.mazaad.security.jwt.JwtBlacklist;
import com.global.mazaad.security.jwt.JwtTokenFromAuthHeaderExtractor;
import com.global.mazaad.security.jwt.JwtValidator;
import com.global.mazaad.security.service.UserAuthenticationService;
import com.global.mazaad.security.service.UserRegistrationService;
import com.global.mazaad.whatsapp.service.WhatsappManager;
import com.global.mazaad.whatsapp.service.WhatsappOtpSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
  private final WhatsappManager whatsappManager;
  private final UserRegistrationService userRegistrationService;

  @Transactional
  @PostMapping("/register")
  public ResponseEntity<?> register(
      @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {

    userRegistrationService.register(userRegistrationRequest);
    return ResponseEntity.status(HttpStatus.SC_CREATED).body("Code sent for activation");
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthenticationResponse> authenticate(
      @Valid @RequestBody final UserAuthenticationRequest request) {

    UserAuthenticationResponse authenticationResponse =
        userAuthenticationService.authenticate(request);

    return ResponseEntity.ok(authenticationResponse);
  }

  @GetMapping("/otp")
  public ResponseEntity<?> requestOtp(@RequestParam String phoneNumber) {
    whatsappManager.sendOtp(phoneNumber);
    return ResponseEntity.ok("Code Sent to: " + phoneNumber);
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
  public ResponseEntity<?> logout() {
    log.info("Received logout request");

    String token = jwtTokenFromAuthHeaderExtractor.extract(httpRequest);

    jwtBlacklist.addToBlacklist(token);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/activate")
  public ResponseEntity<?> activate(
      @RequestParam @NotNull String code, @RequestParam @NotNull String phoneNumber) {
    userRegistrationService.activate(phoneNumber, code);
    return ResponseEntity.ok("User has been Activated Successfully");
  }
}

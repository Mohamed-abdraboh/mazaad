package com.global.mazaad.security.endpoint;

import com.global.mazaad.security.dto.UserAuthenticationRequest;
import com.global.mazaad.security.dto.UserAuthenticationResponse;
import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.security.jwt.JwtAuthenticationProvider;
import com.global.mazaad.security.jwt.JwtBlacklist;
import com.global.mazaad.security.jwt.JwtTokenFromAuthHeaderExtractor;
import com.global.mazaad.security.jwt.JwtValidator;
import com.global.mazaad.security.service.UserAuthenticationService;
import com.global.mazaad.security.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

  //    @PostMapping("/register")
  //    public ResponseEntity<String> register(@RequestBody final UserRegistrationRequest request) {
  ////        log.info("Received registration request for user with email = '{}'", request.email());
  //        emailTokenSender.sendEmailVerificationCode(request);
  ////        log.info("Email verification token sent to the user with email = '{}'",
  // request.email());
  //        return ResponseEntity.ok()
  //                .body(String.format("Email verification token sent to the user with email =
  // %s%nIf You don't receive an email, please check your spam or may be the email address is
  // incorrect", request.email()));
  //    }

  //    @PostMapping(value = "/confirm")
  //    public ResponseEntity<UserRegistrationResponse> confirmEmail(@RequestBody final
  // ConfirmEmailRequest confirmEmailRequest) {
  //        log.info("Received email confirmation request");
  //        UserRegistrationResponse registrationResponse =
  // emailTokenConformer.confirmEmailByCode(confirmEmailRequest);
  //        log.info("Email verification completed");
  //        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
  //    }

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
    //        log.info("Received authentication request for user with email = '{}'",
    // request.email());
    UserAuthenticationResponse authenticationResponse =
        userAuthenticationService.authenticate(request);
    //        log.info("Authentication completed for user with email = '{}'", request.email());
    return ResponseEntity.ok(authenticationResponse);
  }

  @PostMapping("/refresh")
  public ResponseEntity<UserAuthenticationResponse> refreshToken() {
    log.info("Received refresh token request for user");
    var authenticationToken = jwtAuthenticationProvider.get(httpRequest);
    UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationToken.getName());
    UserAuthenticationResponse authenticationResponse =
        userAuthenticationService.authenticate(userDetails, authenticationToken.getName());
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
}

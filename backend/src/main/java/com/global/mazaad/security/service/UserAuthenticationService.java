package com.global.mazaad.security.service;

import com.global.mazaad.security.dto.UserAuthenticationRequest;
import com.global.mazaad.security.dto.UserAuthenticationResponse;
import com.global.mazaad.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

  private static final String INVALID_CREDENTIALS_ERROR_MESSAGE =
      "Invalid credentials for user's account with email = '%s'";

  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  public UserAuthenticationResponse authenticate(final UserAuthenticationRequest request) {
    String userPhoneNumber = request.phoneNumber();
    String userPassword = request.password();

    log.info("Authenticating user with email = '{}'", userPhoneNumber);

    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(userPhoneNumber, userPassword));

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      String jwtRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
      String jwtToken = jwtTokenProvider.generateToken(userDetails);
      log.info("Generated JWT accessToken for user with email = '{}'", request.phoneNumber());

      return new UserAuthenticationResponse(jwtToken, jwtRefreshToken);

    } catch (UsernameNotFoundException exception) {
      log.warn("User with the provided email='{}' does not exist", userPhoneNumber, exception);
      throw new UsernameNotFoundException(
          String.format(INVALID_CREDENTIALS_ERROR_MESSAGE, userPhoneNumber), exception);
    } catch (BadCredentialsException exception) {
      log.warn(
          "Invalid credentials for user's account with email = '{}'", userPhoneNumber, exception);
      throw new BadCredentialsException(
          String.format(INVALID_CREDENTIALS_ERROR_MESSAGE, userPhoneNumber), exception);

    } catch (Exception exception) {
      log.error("Error occurred during authentication", exception);
      throw exception;
    }
  }

  public UserAuthenticationResponse authenticate(
      final UserDetails userDetails, String userPhoneNumber) {
    try {
      String jwtRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
      String jwtToken = jwtTokenProvider.generateToken(userDetails);
      log.info("Generated JWT accessToken for user with email = '{}'", userPhoneNumber);

      return new UserAuthenticationResponse(jwtToken, jwtRefreshToken);
    } catch (BadCredentialsException exception) {
      log.warn(
          "Invalid credentials for user's account with phoneNumber = '{}'",
          userPhoneNumber,
          exception);

      throw new BadCredentialsException(
          String.format(INVALID_CREDENTIALS_ERROR_MESSAGE, userPhoneNumber), exception);

    } catch (Exception exception) {
      log.error("Error occurred during authentication", exception);
      throw exception;
    }
  }
}

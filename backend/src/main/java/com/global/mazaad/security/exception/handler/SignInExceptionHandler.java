package com.global.mazaad.security.exception.handler;

import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import com.global.mazaad.common.exception.handler.ApiErrorResponseCreator;
import com.global.mazaad.common.exception.handler.ErrorDebugMessageCreator;
import com.global.mazaad.security.exception.UserAccountLockedException;
import com.global.mazaad.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@ControllerAdvice
public class SignInExceptionHandler {

  private final ApiErrorResponseCreator apiErrorResponseCreator;
  private final ErrorDebugMessageCreator errorDebugMessageCreator;

  @ExceptionHandler({UserNotFoundException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleUserNotFoundException(final UserNotFoundException exception) {
    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
    log.warn(
        "Handle user not found exception: failed: message: {}, debugMessage: {}",
        apiErrorResponse.getMessage(),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  @ExceptionHandler({UsernameNotFoundException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleUsernameNotFoundException(
      final UsernameNotFoundException exception) {
    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
    log.warn(
        "Handle user not found exception: failed: message: {}, debugMessage: {}",
        apiErrorResponse.getMessage(),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  @ExceptionHandler(UserAccountLockedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleUserAccountLockedException(
      final UserAccountLockedException exception) {
    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
    log.warn(
        "Handle user account locked exception: failed: message: {}, debugMessage: {}",
        apiErrorResponse.getMessage(),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleBadCredentialsException(final BadCredentialsException exception) {
    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
    log.warn(
        "Handle bad credentials exception: failed: message: {}, debugMessage: {}",
        apiErrorResponse.getMessage(),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  //  @ExceptionHandler(JwtTokenExpiredException.class)
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  public ApiErrorResponse handleJwtTokenExpiredException(final JwtTokenExpiredException
  // exception) {
  //    ApiErrorResponse apiErrorResponse =
  //        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
  //    log.warn(
  //        "Handle bad credentials exception: failed: message: {}, debugMessage: {}",
  //        apiErrorResponse.getMessage(),
  //        errorDebugMessageCreator.buildErrorDebugMessage(exception));
  //    return apiErrorResponse;
  //  }
}

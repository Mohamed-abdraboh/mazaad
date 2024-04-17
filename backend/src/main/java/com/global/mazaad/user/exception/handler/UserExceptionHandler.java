package com.global.mazaad.user.exception.handler;

import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import com.global.mazaad.common.exception.handler.ApiErrorResponseCreator;
import com.global.mazaad.common.exception.handler.ErrorDebugMessageCreator;
import com.global.mazaad.user.exception.InvalidOldPasswordException;
import com.global.mazaad.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class UserExceptionHandler {

  private final ApiErrorResponseCreator apiErrorResponseCreator;
  private final ErrorDebugMessageCreator errorDebugMessageCreator;

  @ExceptionHandler(UserNotFoundException.class)
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

  @ExceptionHandler({InvalidOldPasswordException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleInvalidOldPasswordException(
      final InvalidOldPasswordException exception) {
    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception, HttpStatus.UNAUTHORIZED);
    log.warn(
        "Handle user's invalid old password exception: failed: message: {}, debugMessage: {}",
        apiErrorResponse.getMessage(),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }
}

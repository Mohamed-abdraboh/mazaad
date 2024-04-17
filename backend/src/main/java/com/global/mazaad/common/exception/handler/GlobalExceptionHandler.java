package com.global.mazaad.common.exception.handler;

import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ApiErrorResponseCreator apiErrorResponseCreator;
  private final ErrorDebugMessageCreator errorDebugMessageCreator;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException exception) {
    String message =
        exception.getBindingResult().getAllErrors().stream()
            .map(error -> String.format("{ ErrorMessage: %s }", error.getDefaultMessage()))
            .toList()
            .toString();

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(message, HttpStatus.BAD_REQUEST);
    log.error(
        "Handle method argument not valid exception: failed: message: {}, debugMessage: {}.",
        message,
        errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleConstraintViolationException(
      final ConstraintViolationException exception) {
    String message =
        "Validation failed. Please check your input and ensure all required fields are provided correctly.";

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(message, HttpStatus.BAD_REQUEST);
    log.error(
        "Handle constraint violation exception: failed: message: {}, debugMessage: {}.",
        message,
        errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }
}

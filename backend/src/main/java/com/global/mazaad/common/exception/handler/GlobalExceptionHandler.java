package com.global.mazaad.common.exception.handler;

import com.global.mazaad.auction.exception.AuctionEndedException;
import com.global.mazaad.auction.exception.AuctionNotFoundException;
import com.global.mazaad.bid.exception.InvalidBidAmountException;
import com.global.mazaad.common.exception.NotActivatedAccountException;
import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import com.global.mazaad.security.exception.JwtTokenBlacklistedException;
import com.global.mazaad.security.exception.JwtTokenException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

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
            .map(
                error ->
                    String.format(
                        "{ Attribute: %s, ErrorMessage: %s }",
                        ((FieldError) error).getField(), error.getDefaultMessage()))
            .toList()
            .toString();

    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(message);
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
    Set<String> missingAttributes = new HashSet<>();
    Set<String> constraintViolations = new HashSet<>();

    exception
        .getConstraintViolations()
        .forEach(
            constraintViolation -> {
              if (constraintViolation.getPropertyPath() != null) {
                missingAttributes.add(constraintViolation.getPropertyPath().toString());
              }
              if (constraintViolation.getMessage() != null) {
                constraintViolations.add(constraintViolation.getMessage());
              }
            });

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(
            "Missing attribute(s): "
                + String.join(", ", missingAttributes)
                + ". Constraint violation(s): "
                + String.join(", ", constraintViolations));

    log.error(
        "Handling constraint violation exception: failed: missing attributes: {}, constraint violations: {}, debugMessage: {}.",
        String.join(", ", missingAttributes),
        String.join(", ", constraintViolations),
        errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(AuctionEndedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiErrorResponse handleAuctionEndedException(final AuctionEndedException exception) {

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception.getMessage());
    log.error(exception.getMessage(), errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(AuctionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleAuctionNotFoundException(final AuctionNotFoundException exception) {

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception.getMessage());
    log.error(exception.getMessage(), errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(InvalidBidAmountException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleInvalidBidAmountException(
      final InvalidBidAmountException exception) {

    ApiErrorResponse apiErrorResponse =
        apiErrorResponseCreator.buildResponse(exception.getMessage());
    log.error(exception.getMessage(), errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(JwtTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // Set appropriate HTTP status code
  public ApiErrorResponse handleJwtTokenException(final JwtTokenException exception) {
    // Customize error message and response as needed
    String errorMessage = exception.getMessage();
    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(errorMessage);
    log.error(errorMessage, errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  @ExceptionHandler(JwtTokenBlacklistedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // Set appropriate HTTP status code
  public ApiErrorResponse handleJwtTokenBlacklistedException(
      final JwtTokenBlacklistedException exception) {
    // Customize error message and response as needed
    String errorMessage = exception.getMessage();
    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(errorMessage);
    log.error(errorMessage, errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }

  @ExceptionHandler(NotActivatedAccountException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN) // Set appropriate HTTP status code
  public ApiErrorResponse handleNotActivatedAccountException(
      final NotActivatedAccountException exception) {
    // Customize error message and response as needed
    String errorMessage = exception.getMessage();
    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(errorMessage);
    log.error(errorMessage, errorDebugMessageCreator.buildErrorDebugMessage(exception));
    return apiErrorResponse;
  }
}

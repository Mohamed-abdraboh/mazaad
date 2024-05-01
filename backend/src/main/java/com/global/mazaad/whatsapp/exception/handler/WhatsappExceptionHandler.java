package com.global.mazaad.whatsapp.exception.handler;

import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import com.global.mazaad.common.exception.handler.ApiErrorResponseCreator;
import com.global.mazaad.common.exception.handler.ErrorDebugMessageCreator;
import com.global.mazaad.whatsapp.exception.TokenNotFoundException;
import com.global.mazaad.whatsapp.exception.TokenNotSentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class WhatsappExceptionHandler {

  private final ApiErrorResponseCreator apiErrorResponseCreator;
  private final ErrorDebugMessageCreator errorDebugMessageCreator;

  @ExceptionHandler(TokenNotSentException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handleTokenNotSentException(final TokenNotSentException exception) {
    String message = exception.getMessage();

    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(message);
    log.error(
        "Handle Token not sent exception: failed: message: {}, debugMessage: {}.",
        message,
        errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }

  @ExceptionHandler(TokenNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleTokenNotFoundException(final TokenNotFoundException exception) {
    String message = exception.getMessage();

    ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(message);
    log.error(
        "Handle Token not found exception: failed: message: {}, debugMessage: {}.",
        message,
        errorDebugMessageCreator.buildErrorDebugMessage(exception));

    return apiErrorResponse;
  }
}

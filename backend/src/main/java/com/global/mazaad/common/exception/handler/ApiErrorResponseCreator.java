package com.global.mazaad.common.exception.handler;

import com.global.mazaad.common.exception.dto.ApiErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ApiErrorResponseCreator {

  public ApiErrorResponse buildResponse(String errorMessage) {
    return ApiErrorResponse.builder().message(errorMessage).timestamp(LocalDateTime.now()).build();
  }

  public ApiErrorResponse buildResponse(Exception exception) {
    return buildResponse(exception.getMessage());
  }
}

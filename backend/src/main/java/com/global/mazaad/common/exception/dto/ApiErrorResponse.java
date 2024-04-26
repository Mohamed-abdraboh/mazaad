package com.global.mazaad.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
  public static final String TIMESTAMP_JSON_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @JsonProperty("message")
  String message;

  @JsonProperty("timestamp")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP_JSON_FORMAT)
  LocalDateTime timestamp;
}

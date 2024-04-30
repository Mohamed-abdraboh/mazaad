package com.global.mazaad.whatsapp;

import com.global.mazaad.whatsapp.exception.TokenNotSentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WhatsappOtpSender {

  @Value("${whatsapp.api-url}")
  private String API_URL;

  private final HttpEntityBuilder httpEntityBuilder;

  public void sendOtp(String destinationPhoneNumber, String messageCode) {
    // Create RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();

    // Send POST request
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        httpEntityBuilder.build(destinationPhoneNumber, messageCode);
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

    HttpStatusCode statusCode = responseEntity.getStatusCode();
    if (statusCode != HttpStatus.OK) {
      throw new TokenNotSentException();
    }
  }
}

package com.global.mazaad.whatsapp.service;

import com.global.mazaad.whatsapp.exception.TokenNotSentException;
import com.global.mazaad.whatsapp.service.HttpEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WhatsappOtpSender {

  @Value("${whatsapp.api-url}")
  private String API_URL;

  private final HttpEntityBuilder httpEntityBuilder;
  private final String EGYPT_PHONE_NUMBER_PREFIX = "2";

  public void sendOtp(String destinationPhoneNumber, String messageCode) {
    destinationPhoneNumber = EGYPT_PHONE_NUMBER_PREFIX + destinationPhoneNumber;

    // Create RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();

    // Send POST request
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        httpEntityBuilder.build("201551255561", messageCode);
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

    HttpStatusCode statusCode = responseEntity.getStatusCode();
    if (statusCode != HttpStatus.ACCEPTED) {
      System.out.println("******************");
      System.out.println(responseEntity);
      throw new TokenNotSentException(responseEntity.getBody());
    }
  }
}

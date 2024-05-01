package com.global.mazaad.whatsapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class HttpEntityBuilder {

  @Value("${whatsapp.api-key}")
  private String API_KEY;

  @Value("${whatsapp.source-name}")
  private String SOURCE_NAME;

  @Value("${whatsapp.source-number}")
  private String SOURCE_NUMBER;

  @Value("${whatsapp.template-id}")
  private String TEMPLATE_ID;

  HttpEntity<MultiValueMap<String, String>> build(
      String destinationPhoneNumber, String messageCode) {
    // Set headers
    HttpHeaders headers = new HttpHeaders();
    headers.set("apikey", API_KEY);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setCacheControl(HttpHeaders.CACHE_CONTROL); // Use predefined constant

    // Set form data
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("channel", "whatsapp");
    formData.add("source", SOURCE_NUMBER);
    formData.add("destination", destinationPhoneNumber); // Double curly braces
    formData.add("src.name", SOURCE_NAME);
    String templateJson =
        "{\"id\":\""
            + TEMPLATE_ID
            + "\",\"params\":[\""
            + messageCode
            + "\",\""
            + messageCode
            + "\"]}";
    formData.add("template", templateJson); // No single quotes for JSON string

    // Send POST request
    return new HttpEntity<>(formData, headers);
  }
}

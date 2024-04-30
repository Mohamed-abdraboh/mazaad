package com.global.mazaad.storage.service;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
public class AwsUrlBuilder {
  @Value("${cloud.aws.region.static}")
  private String region;

  @Value("${cloud.aws.bucket.name}")
  private String bucketName;

  public String build(String objectName) {

    return String.format("https://s3.%s.amazonaws.com/%s/%s", region, bucketName, objectName);
  }
}

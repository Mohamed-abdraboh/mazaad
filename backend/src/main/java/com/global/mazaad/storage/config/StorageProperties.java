package com.global.mazaad.storage.config;

import java.io.File;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
public class StorageProperties {

  /** Folder location for storing files */
  private String location = System.getProperty("user.dir") + File.separator + "images";

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

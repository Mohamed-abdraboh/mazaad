package com.global.mazaad.itemsOffer.config;

import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import com.global.mazaad.storage.service.StorageService;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemsOfferEntityListener {

  private static StorageService storageService;

  @Autowired
  public void init(StorageService storageService) {
    ItemsOfferEntityListener.storageService = storageService;
  }

  @PreRemove
  public void preRemove(ItemsOffer itemsOffer) {
    itemsOffer
        .getImagesUrls()
        .forEach(
            url -> {
              String objectName = extractObjectNameFromUrl(url);
              storageService.deleteFile(objectName);
            });
  }

  private String extractObjectNameFromUrl(String url) {
    String[] parts = url.split("/");
    return parts[parts.length - 1];
  }
}

package com.global.mazaad.itemsOffer.service;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import com.global.mazaad.itemsOffer.mapper.ItemsOfferMapper;
import com.global.mazaad.itemsOffer.repository.ItemsOfferRepository;
import com.global.mazaad.storage.service.AwsUrlBuilder;
import com.global.mazaad.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsOfferService {
  private final ItemsOfferMapper itemsOfferMapper;
  private final ItemsOfferRepository itemsOfferRepository;
  private final StorageService storageService;
  private final AwsUrlBuilder awsUrlBuilder;

  public ItemsOffer createItemsOffer(ItemsOfferDto itemsOfferDto) {
    ItemsOffer itemsOffer = itemsOfferMapper.mapToItemsOffer(itemsOfferDto);
    return itemsOfferRepository.save(itemsOffer);
  }

  public List<String> addImage(Long itemsOfferId, MultipartFile[] files) {
    List<String> urls = new ArrayList<>();
    for (MultipartFile file : files) {
      String fileName = storageService.uploadFile(file);
      String imageUrl = awsUrlBuilder.build(fileName);
      itemsOfferRepository.addImageToItemsOffer(itemsOfferId, imageUrl);
      urls.add(imageUrl);
    }
    return urls;
  }

  public List<String> getAllImagesUrls(Long itemsOfferId) {
    return itemsOfferRepository.getImagesById(itemsOfferId);
  }

  public Long addImageToItemsOffer(Long itemsOfferId, String imagePath) {
    return Long.valueOf(itemsOfferRepository.addImageToItemsOffer(itemsOfferId, imagePath));
  }
}

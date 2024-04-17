package com.global.mazaad.itemsOffer.service;


import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import com.global.mazaad.itemsOffer.mapper.ItemsOfferMapper;
import com.global.mazaad.itemsOffer.repository.ItemsOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemsOfferService {
  private final ItemsOfferMapper itemsOfferMapper;
  private final ItemsOfferRepository itemsOfferRepository;

  public ItemsOffer createItemsOffer(ItemsOfferDto itemsOfferDto) {
    ItemsOffer itemsOffer = itemsOfferMapper.mapToItemsOffer(itemsOfferDto);
    return itemsOfferRepository.save(itemsOffer);
  }

  public Long addImageToItemsOffer(Long itemsOfferId, String imagePath) {
    return Long.valueOf(itemsOfferRepository.addImageToItemsOffer(itemsOfferId, imagePath));
  }
}

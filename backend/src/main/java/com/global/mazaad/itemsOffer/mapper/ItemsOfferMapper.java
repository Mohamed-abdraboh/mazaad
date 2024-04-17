package com.global.mazaad.itemsOffer.mapper;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemsOfferMapper {
  ItemsOffer mapToItemsOffer(ItemsOfferDto itemsOfferDto);

  ItemsOfferDto mapToDto(ItemsOffer itemsOffer);
}

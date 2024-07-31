package com.global.mazaad.itemsOffer.mapper;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import com.global.mazaad.itemsOffer.dto.ItemsOfferResponseDto;
import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemsOfferMapper {
  @Mapping(target = "imagesUrls", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "associatedAuction", ignore = true)
  @Mapping(target = "type", source = "type", qualifiedByName = "toUpperCase")
  ItemsOffer mapToItemsOffer(ItemsOfferDto itemsOfferDto);

  ItemsOfferDto mapToDto(ItemsOffer itemsOffer);

  ItemsOfferResponseDto mapToResponseDto(ItemsOffer itemsOffer);

  @Named("toUpperCase")
  default String toUpperCase(String value) {
    return value != null ? value.toUpperCase() : null;
  }
}

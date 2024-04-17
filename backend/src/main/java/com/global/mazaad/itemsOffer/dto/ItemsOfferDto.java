package com.global.mazaad.itemsOffer.dto;

import com.global.mazaad.itemsOffer.constant.ItemType;
import com.global.mazaad.itemsOffer.constant.Unit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ItemsOfferDto {
  @Enumerated(EnumType.STRING)
  private Unit unit;

  private Double quantity;

  @Enumerated(EnumType.STRING)
  private ItemType type;

  private String location;
}

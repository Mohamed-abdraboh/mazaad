package com.global.mazaad.itemsOffer.dto;

import com.global.mazaad.itemsOffer.constant.ItemType;
import com.global.mazaad.itemsOffer.constant.Unit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;

@Setter
@Getter
@Builder
public class ItemsOfferDto {
  @NotNull(message = "ItemsOffer Unit must not be null") private Unit unit;
  @NotNull(message = "ItemsOffer quantity must not be null") private Double quantity;

  @NotNull(message = "ItemsOffer Unit must not be null") private ItemType type;
  @NotNull(message = "ItemsOffer location must not be null") private String location;
}

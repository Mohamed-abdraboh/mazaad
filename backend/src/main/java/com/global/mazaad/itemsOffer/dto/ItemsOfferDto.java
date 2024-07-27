package com.global.mazaad.itemsOffer.dto;

import com.global.mazaad.itemsOffer.constant.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ItemsOfferDto {
  @NotNull(message = "ItemsOffer Unit must not be null")
  private Unit unit;

  @NotNull(message = "ItemsOffer quantity must not be null")
  private Double quantity;

  @NotNull(message = "ItemsOffer Unit must not be null")
  private String type;

  @NotNull(message = "ItemsOffer location must not be null")
  private String location;
}

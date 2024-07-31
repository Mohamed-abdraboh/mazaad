package com.global.mazaad.itemsOffer.dto;

import com.global.mazaad.itemsOffer.constant.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ItemsOfferResponseDto {

  private Unit unit;
  private Double quantity;
  private String type;
  private String location;
  private List<String> imagesUrls;
}

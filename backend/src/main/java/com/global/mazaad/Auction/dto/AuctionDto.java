package com.global.mazaad.Auction.dto;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AuctionDto {
  @NotNull private LocalDateTime startDate;
  @NotNull private LocalDateTime endDate;
  @NotNull private double startPrice;
  @OneToOne private ItemsOfferDto itemsOffer;
  private String description;
}

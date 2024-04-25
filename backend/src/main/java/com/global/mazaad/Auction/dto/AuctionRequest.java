package com.global.mazaad.Auction.dto;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AuctionRequest {
  @NotNull(message = "startDateTime can't be null") private LocalDateTime startDateTime;
  @NotNull(message = "endDateTime can't be null") private LocalDateTime endDateTime;
  @NotNull(message = "startPrice can't be null") private Double startPrice;
  @Valid private ItemsOfferDto itemsOffer;
  @NotNull(message = "description can't be null") private String description;
}

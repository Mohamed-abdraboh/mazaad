package com.global.mazaad.auction.dto;

import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

@Setter
@Getter
public class AuctionResponse {

  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Double startPrice;
  private ItemsOfferDto itemsOffer;
  private String description;
  private PriorityQueue<Double> bids;
}

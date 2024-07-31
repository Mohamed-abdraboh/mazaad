package com.global.mazaad.auction.dto;

import com.global.mazaad.bid.dto.BidResponse;
import com.global.mazaad.itemsOffer.dto.ItemsOfferDto;
import com.global.mazaad.itemsOffer.dto.ItemsOfferResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

@Setter
@Getter
public class AuctionResponse {

  private Long id;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Double startPrice;
  private ItemsOfferResponseDto itemsOfferResponseDto;
  private String description;
  private List<BidResponse> bids;
}

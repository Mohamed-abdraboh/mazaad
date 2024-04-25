package com.global.mazaad.bid.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidRequest {
  @NotNull private Double amount;
  @NotNull private Long auctionId;
}

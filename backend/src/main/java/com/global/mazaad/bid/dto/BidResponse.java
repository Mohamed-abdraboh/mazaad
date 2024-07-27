package com.global.mazaad.bid.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidResponse {
  private Double amount;
  private Long userId;
}

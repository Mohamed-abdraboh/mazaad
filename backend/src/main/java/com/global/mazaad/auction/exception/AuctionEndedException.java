package com.global.mazaad.auction.exception;

import java.time.LocalDateTime;

public class AuctionEndedException extends RuntimeException {
  public AuctionEndedException(LocalDateTime endDateTime) {
    super(String.format("Auction ended with dateTime = %s", endDateTime));
  }
}

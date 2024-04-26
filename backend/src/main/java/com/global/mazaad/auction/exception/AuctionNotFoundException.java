package com.global.mazaad.auction.exception;

public class AuctionNotFoundException extends RuntimeException {
  public AuctionNotFoundException(Long auctionId) {
    super(String.format("Auction not found with id = %s", auctionId));
  }
}

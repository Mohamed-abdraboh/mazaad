package com.global.mazaad.bid.exception;

public class InvalidBidAmountException extends RuntimeException {
  public InvalidBidAmountException(Double highestBidAmount) {
    super(String.format("Bid amount must exceed the highest bid: %s", highestBidAmount));
  }
}

package com.global.mazaad.bid.service;

import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.auction.exception.AuctionEndedException;
import com.global.mazaad.bid.entity.Bid;
import com.global.mazaad.bid.exception.InvalidBidAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidValidator {

  public void validate(Bid bid) {
    validateIfAuctionEnded(bid);
    validateIfNotHighestBid(bid);
  }

  public void validateIfAuctionEnded(Bid bid) {
    Auction auction = bid.getAuction();
    LocalDateTime endDateTime = auction.getEndDateTime();
    if (endDateTime.isBefore(LocalDateTime.now())) throw new AuctionEndedException(endDateTime);
  }

  public void validateIfNotHighestBid(Bid bid) {
    List<Bid> bids = bid.getAuction().getBids();
    bids.sort(Comparator.comparingDouble(Bid::getAmount).reversed());
    double highestAmount =
        bids.isEmpty() ? bid.getAuction().getStartPrice() : bids.get(0).getAmount();

    if (bid.getAmount() <= highestAmount) {
      throw new InvalidBidAmountException(highestAmount);
    }
  }
}

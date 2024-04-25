package com.global.mazaad.Auction.mapper;

import com.global.mazaad.Auction.dto.AuctionRequest;
import com.global.mazaad.Auction.dto.AuctionResponse;
import com.global.mazaad.Auction.entity.Auction;
import com.global.mazaad.itemsOffer.mapper.ItemsOfferMapper;
import com.global.mazaad.bid.entity.Bid;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(uses = ItemsOfferMapper.class)
public interface AuctionMapper {

  Auction mapToAuction(AuctionRequest auctionRequest);

  AuctionRequest mapToRequest(Auction auction);

  @Mapping(target = "bids", expression = "java(mapBidsToPriorityQueue(auction))")
  AuctionResponse mapToResponse(Auction auction);

  default PriorityQueue<Double> mapBidsToPriorityQueue(Auction auction) {
    return auction.getBids().stream()
        .map(Bid::getAmount)
        .collect(Collectors.toCollection(PriorityQueue::new));
  }
}

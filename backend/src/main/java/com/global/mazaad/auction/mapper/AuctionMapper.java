package com.global.mazaad.auction.mapper;

import com.global.mazaad.auction.dto.AuctionRequest;
import com.global.mazaad.auction.dto.AuctionResponse;
import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.bid.mapper.BidResponseMapper;
import com.global.mazaad.itemsOffer.mapper.ItemsOfferMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ItemsOfferMapper.class, BidResponseMapper.class})
public interface AuctionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bids", ignore = true)
  Auction mapToAuction(AuctionRequest auctionRequest);

  AuctionRequest mapToRequest(Auction auction);

  @Mapping(target = "bids", source = "bids", qualifiedByName = "mapBidListToBidResponseList")
  AuctionResponse mapToResponse(Auction auction);

}

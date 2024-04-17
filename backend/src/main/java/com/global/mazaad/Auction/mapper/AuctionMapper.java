package com.global.mazaad.Auction.mapper;

import com.global.mazaad.Auction.dto.AuctionDto;
import com.global.mazaad.Auction.entity.Auction;
import com.global.mazaad.itemsOffer.mapper.ItemsOfferMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = ItemsOfferMapper.class)
@Component
public interface AuctionMapper {
  Auction mapToAuction(AuctionDto auctionDto);

  AuctionDto mapToDto(Auction auction);
}

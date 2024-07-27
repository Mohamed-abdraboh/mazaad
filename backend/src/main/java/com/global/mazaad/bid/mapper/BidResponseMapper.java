package com.global.mazaad.bid.mapper;

import com.global.mazaad.bid.dto.BidResponse;
import com.global.mazaad.bid.entity.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface BidResponseMapper {

  @Mapping(target = "userName", source = "user.name")
  @Mapping(target = "userId", source = "user.id")
  BidResponse mapToResponse(Bid bid);

  @Named("mapBidListToBidResponseList")
  List<BidResponse> mapBidListToBidResponseList(List<Bid> bids);
}

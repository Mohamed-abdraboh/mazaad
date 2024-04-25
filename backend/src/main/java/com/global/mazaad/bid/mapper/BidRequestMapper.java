package com.global.mazaad.bid.mapper;

import com.global.mazaad.bid.dto.BidRequest;
import com.global.mazaad.bid.entity.Bid;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BidRequestMapper {
  Bid toBid(BidRequest bidRequest);

  BidRequest toRequest(Bid bid);
}

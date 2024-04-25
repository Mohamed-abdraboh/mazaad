package com.global.mazaad.bid.service;

import com.global.mazaad.Auction.entity.Auction;
import com.global.mazaad.Auction.service.AuctionService;
import com.global.mazaad.bid.dto.BidRequest;
import com.global.mazaad.bid.entity.Bid;
import com.global.mazaad.bid.mapper.BidRequestMapper;
import com.global.mazaad.bid.repository.BidRepository;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.repository.UserRepository;
import com.global.mazaad.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {
  private final BidRepository bidRepository;
  private final UserService userService;
  private final BidRequestMapper bidRequestMapper;
  private final AuctionService auctionService;
  private final BidValidator bidValidator;

  public Long bid(BidRequest bidRequest) {
    String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userService.findByPhoneNumber(phoneNumber);
    Auction auction = auctionService.findById(bidRequest.getAuctionId());

    Bid bid = bidRequestMapper.toBid(bidRequest);
    bid.setUser(user);
    bid.setAuction(auction);

    bidValidator.validate(bid);
    Bid savedBid = bidRepository.save(bid);
    return savedBid.getId();
  }
}

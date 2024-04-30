package com.global.mazaad.auction.service;

import com.global.mazaad.auction.dto.AuctionRequest;
import com.global.mazaad.auction.dto.AuctionResponse;
import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.auction.exception.AuctionNotFoundException;
import com.global.mazaad.auction.mapper.AuctionMapper;
import com.global.mazaad.auction.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionService {
  private final AuctionRepository auctionRepository;
  private final AuctionMapper auctionMapper;

  @Transactional
  public Long createAuction(AuctionRequest auctionRequest) {
    Auction auction = auctionMapper.mapToAuction(auctionRequest);
    auction = auctionRepository.save(auction);
    return auction.getId();
  }

  public List<AuctionResponse> getAuctions(int pageSize) {
    List<Auction> auctions = auctionRepository.findAll(Pageable.ofSize(pageSize)).toList();

    return auctions.stream().map(auctionMapper::mapToResponse).collect(Collectors.toList());
  }

  @Transactional
  public void deleteAuction(Long auctionId) {
    auctionRepository.deleteById(auctionId);
  }

  @Transactional
  public void modifyAuction(Long id, AuctionRequest auctionRequest) {
    if (!auctionRepository.existsById(id)) return;
    Auction auction = auctionMapper.mapToAuction(auctionRequest);
    auction.setId(id);
    auctionRepository.save(auction);
  }

  public Auction findById(Long id) {
    return auctionRepository.findById(id).orElseThrow(() -> new AuctionNotFoundException(id));
  }
}

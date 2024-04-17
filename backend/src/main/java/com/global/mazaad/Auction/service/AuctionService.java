package com.global.mazaad.Auction.service;

import com.global.mazaad.Auction.dto.AuctionDto;
import com.global.mazaad.Auction.entity.Auction;
import com.global.mazaad.Auction.mapper.AuctionMapper;
import com.global.mazaad.Auction.repository.AuctionRepository;
import com.global.mazaad.storage.service.FileSystemStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {
  private final AuctionRepository auctionRepository;
  private final AuctionMapper auctionMapper;
  private final FileSystemStorageService fileSystemStorageService;

  @Transactional
  public Long createAuction(AuctionDto auctionDto) {
    Auction auction = auctionMapper.mapToAuction(auctionDto);
    auction = auctionRepository.save(auction);
    return auction.getId();
  }

  @Transactional
  public void deleteAuction(Long auctionId) {
    auctionRepository.deleteById(auctionId);
  }

  @Transactional
  public void modifyAuction(Long id, AuctionDto auctionDto) {
    if (!auctionRepository.existsById(id)) return;
    Auction auction = auctionMapper.mapToAuction(auctionDto);
    auction.setId(id);
    auctionRepository.save(auction);
  }

  public Auction findById(Long id) {
    return auctionRepository.findById(id).orElseThrow();
  }
}

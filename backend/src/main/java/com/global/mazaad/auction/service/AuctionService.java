package com.global.mazaad.auction.service;

import com.global.mazaad.auction.dto.AuctionRequest;
import com.global.mazaad.auction.dto.AuctionResponse;
import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.auction.exception.AuctionNotFoundException;
import com.global.mazaad.auction.mapper.AuctionMapper;
import com.global.mazaad.auction.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public Page<AuctionResponse> getAuctions(
      int pageSize, int pageNumber, String sortDirection, String type) {

    // Setting a default sort property
    String sortBy = "startDateTime";

    Pageable pageable =
        PageRequest.of(
            pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

    Page<Auction> auctionPage;
    if (type != null && !type.isEmpty()) {
      String itemType = type.toUpperCase();
      auctionPage = auctionRepository.findByItemsOffer_Type(itemType, pageable);
    } else {
      auctionPage = auctionRepository.findAll(pageable);
    }

    // Map the content of the page to AuctionResponse

    return auctionPage.map(auctionMapper::mapToResponse);
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

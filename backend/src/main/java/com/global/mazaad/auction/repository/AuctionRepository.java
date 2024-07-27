package com.global.mazaad.auction.repository;

import com.global.mazaad.auction.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

  Page<Auction> findByItemsOffer_Type(String type, Pageable pageable);
}

package com.global.mazaad.auction.repository;

import com.global.mazaad.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {}

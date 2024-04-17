package com.global.mazaad.Auction.repository;

import com.global.mazaad.Auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {}

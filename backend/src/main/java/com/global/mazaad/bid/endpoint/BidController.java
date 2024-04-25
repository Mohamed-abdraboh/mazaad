package com.global.mazaad.bid.endpoint;

import com.global.mazaad.bid.dto.BidRequest;
import com.global.mazaad.bid.repository.BidRepository;
import com.global.mazaad.bid.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bids")
@RequiredArgsConstructor
public class BidController {
  private final BidService bidService;

  @PostMapping
  public ResponseEntity<?> bid(@RequestBody BidRequest bidRequest) {
    Long id = bidService.bid(bidRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("Bid is created with id: " + id);
  }
}

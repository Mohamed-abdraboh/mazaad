package com.global.mazaad.auction.endpoint;

import com.global.mazaad.auction.dto.AuctionRequest;
import com.global.mazaad.auction.dto.AuctionResponse;
import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.auction.service.AuctionService;
import com.global.mazaad.itemsOffer.service.ItemsOfferService;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auctions")
public class AuctionController {
  private final AuctionService auctionService;
  private final ItemsOfferService itemsOfferService;

  @Operation(summary = "Create new auction.")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionRequest auctionRequest) {
    Long auctionId = auctionService.createAuction(auctionRequest);
    return ResponseEntity.created(URI.create("/resources/" + auctionId))
        .body("Auction created with id " + auctionId);
  }

  @Operation(summary = "Retrieve auctions.")
  @GetMapping
  public ResponseEntity<?> getAuctions(@RequestParam(defaultValue = "10") int pageSize) {
    List<AuctionResponse> auctionResponses = auctionService.getAuctions(pageSize);
    return ResponseEntity.ok(auctionResponses);
  }

  @Operation(summary = "Delete an auction.")
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAuction(@PathVariable Long id) {
    auctionService.deleteAuction(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Modify existing auction.")
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<?> ModifyAuction(
      @PathVariable Long id, @RequestBody AuctionRequest auctionRequest) {
    auctionService.modifyAuction(id, auctionRequest);
    return ResponseEntity.ok("Auction updated with id +" + id);
  }

  @Operation(summary = "Upload image.")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{id}/images")
  public ResponseEntity<?> uploadImage(
      @PathVariable Long id, @RequestParam("file") MultipartFile[] multipartFile) {
    Auction auction = auctionService.findById(id);
    Long itemsOfferId = auction.getItemsOffer().getId();
    List<String> imagesUrls = itemsOfferService.addImage(itemsOfferId, multipartFile);
    return ResponseEntity.status(HttpStatus.SC_CREATED).body(imagesUrls);
  }

  @Operation(summary = "Get all images urls.")
  @GetMapping("/{id}/images")
  public ResponseEntity<?> downloadImages(@PathVariable Long id) {
    Auction auction = auctionService.findById(id);
    Long itemsOfferId = auction.getItemsOffer().getId();
    return ResponseEntity.ok(itemsOfferService.getAllImagesUrls(itemsOfferId));
  }
}

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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
  @PostMapping
  public ResponseEntity<?> createAuction(
          @Valid @RequestPart AuctionRequest auctionRequest,
          @RequestPart(value = "image", required = false) MultipartFile[] multipartFile) {
    Long auctionId = auctionService.createAuction(auctionRequest, multipartFile);
    return ResponseEntity.created(URI.create("/resources/" + auctionId))
            .body("Auction created with id " + auctionId);
  }


  @Operation(summary = "Retrieve auctions.")
  @GetMapping
  public ResponseEntity<?> getAuctions(
          @RequestParam(defaultValue = "10") int pageSize,
          @RequestParam(defaultValue = "0") int pageNumber,
          @RequestParam(defaultValue = "asc") String sortDirection,
          @RequestParam(required = false) String type) {

    Page<AuctionResponse> auctionResponses =
            auctionService.getAuctions(pageSize, pageNumber, sortDirection, type);
    return ResponseEntity.ok(auctionResponses);
  }

  @Operation(summary = "Delete an auction.")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAuction(@PathVariable Long id) {
    auctionService.deleteAuction(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Modify existing auction.")
  @PutMapping("/{id}")
  public ResponseEntity<?> modifyAuction(
          @PathVariable Long id, @RequestBody AuctionRequest auctionRequest) {
    auctionService.modifyAuction(id, auctionRequest);
    return ResponseEntity.ok("Auction updated with id " + id);
  }

  @Operation(summary = "Upload image.")
  @PostMapping("/{id}/images")
  public ResponseEntity<?> uploadImage(
          @PathVariable Long id, @RequestParam("image") MultipartFile[] multipartFiles) {
    Auction auction = auctionService.findById(id);
    Long itemsOfferId = auction.getItemsOffer().getId();
    List<String> imagesUrls = itemsOfferService.addImage(itemsOfferId, multipartFiles);
    return ResponseEntity.status(HttpStatus.CREATED).body(imagesUrls);
  }
}

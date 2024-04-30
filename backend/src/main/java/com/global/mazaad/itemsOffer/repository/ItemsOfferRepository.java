package com.global.mazaad.itemsOffer.repository;

import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemsOfferRepository extends JpaRepository<ItemsOffer, Long> {
  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value = "INSERT INTO offer_image(offer_id, image_url) VALUES (:itemsOfferId, :imageUrl)")
  Integer addImageToItemsOffer(Long itemsOfferId, String imageUrl);

  @Query(
      nativeQuery = true,
      value = "SELECT image_url FROM offer_image where offer_id = :itemOfferId")
  List<String> getImagesById(Long itemOfferId);
}

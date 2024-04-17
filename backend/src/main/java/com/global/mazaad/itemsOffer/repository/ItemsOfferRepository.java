package com.global.mazaad.itemsOffer.repository;

import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemsOfferRepository extends JpaRepository<ItemsOffer, Long> {
  @Modifying
  @Transactional
  @Query(
      nativeQuery = true,
      value = "INSERT INTO offer_image(offer_id, image_path) VALUES (:itemsOfferId, :imagePath)")
  Integer addImageToItemsOffer(Long itemsOfferId, String imagePath);

  @Query(
      nativeQuery = true,
      value = "SELECT image_path FROM offer_image where offer_id = :itemOfferId")
  String findImagePathById(Long itemOfferId);
}

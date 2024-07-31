package com.global.mazaad.itemsOffer.entity;

import com.global.mazaad.auction.entity.Auction;
import com.global.mazaad.common.auditing.AuditedEntity;
import com.global.mazaad.itemsOffer.config.ItemsOfferEntityListener;
import com.global.mazaad.itemsOffer.constant.Unit;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(ItemsOfferEntityListener.class)
public class ItemsOffer extends AuditedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Unit unit;

  private Double quantity;

  private String type;

  private String location;

  @OneToOne(mappedBy = "itemsOffer")
  private Auction associatedAuction;

  @ElementCollection
  @CollectionTable(name = "offer_image", joinColumns = @JoinColumn(name = "offer_id"))
  @Column(name = "image_url")
  private List<String> imagesUrls;
}

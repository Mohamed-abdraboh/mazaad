package com.global.mazaad.Auction.entity;

import com.global.mazaad.bid.entity.Bid;
import com.global.mazaad.common.auditing.AuditedEntity;
import com.global.mazaad.itemsOffer.entity.ItemsOffer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auction extends AuditedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private LocalDateTime startDate;
  @NotNull private LocalDateTime endDate;
  @NotNull private double startPrice;

  @OneToOne(cascade = CascadeType.ALL)
  private ItemsOffer itemsOffer;

  private String description;

  @OneToMany(mappedBy = "auction")
  private List<Bid> bids;
}

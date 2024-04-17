package com.global.mazaad.bid.entity;

import com.global.mazaad.Auction.entity.Auction;
import com.global.mazaad.common.auditing.AuditedEntity;
import com.global.mazaad.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid extends AuditedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private double amount;

  @ManyToOne(optional = false)
  private User user;

  @ManyToOne(optional = false)
  private Auction auction;
}

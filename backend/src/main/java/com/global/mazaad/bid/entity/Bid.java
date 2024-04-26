package com.global.mazaad.bid.entity;

import com.global.mazaad.auction.entity.Auction;
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
public class Bid extends AuditedEntity implements Comparable<Bid> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private Double amount;

  @ManyToOne(optional = false)
  private User user;

  @ManyToOne(optional = false)
  private Auction auction;

  @Override
  public int compareTo(Bid bid) {
    return this.amount.compareTo(bid.amount);
  }
}

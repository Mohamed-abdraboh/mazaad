package com.global.mazaad.whatsapp.token.entity;

import com.global.mazaad.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String value;
  private LocalDateTime expiryDateTime;
  @OneToOne private User user;
}

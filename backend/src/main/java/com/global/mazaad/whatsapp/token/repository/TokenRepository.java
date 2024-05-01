package com.global.mazaad.whatsapp.token.repository;

import com.global.mazaad.user.entity.User;
import com.global.mazaad.whatsapp.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
  Optional<Token> findByValue(String value);

  Optional<Token> findByUser(User user);
}

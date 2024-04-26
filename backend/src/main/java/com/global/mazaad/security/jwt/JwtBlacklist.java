package com.global.mazaad.security.jwt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.global.mazaad.security.entity.BlockedToken;
import com.global.mazaad.security.repository.BlockedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklist {
  private final JwtClaimExtractor jwtClaimExtractor;
  private final BlockedTokenRepository blockedTokenRepository;

  public void addToBlacklist(String token) {
    BlockedToken blockedToken =
        new BlockedToken(token, jwtClaimExtractor.extractExpirationDate(token));
    blockedTokenRepository.save(blockedToken);
  }

  public boolean contains(String jwtToken) {
    return blockedTokenRepository.findById(jwtToken).isPresent();
  }
}

package com.global.mazaad.security.jwt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklist {

  private final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());

  public void addToBlacklist(String token) {
    blacklistedTokens.add(token);
  }

  public boolean contains(String jwtToken) {
    return blacklistedTokens.contains(jwtToken);
  }
}

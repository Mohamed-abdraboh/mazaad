package com.global.mazaad.security.repository;

import com.global.mazaad.security.entity.BlockedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedTokenRepository extends JpaRepository<BlockedToken, String> {}

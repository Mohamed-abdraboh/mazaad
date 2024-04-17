package com.global.mazaad.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtSignKeyProvider {

	@Value("${application.security.jwt.secret.secret-key}")
	private String secretKey;

	@Value("${application.security.jwt.secret.refresh-secret-key}")
	private String secretRefreshKey;

	public Key get() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Key getRefresh() {
		byte[] keyBytes = Decoders.BASE64.decode(secretRefreshKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

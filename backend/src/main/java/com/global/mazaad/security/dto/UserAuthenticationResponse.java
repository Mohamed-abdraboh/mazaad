package com.global.mazaad.security.dto;

public record UserAuthenticationResponse(String token, String refreshToken) {}
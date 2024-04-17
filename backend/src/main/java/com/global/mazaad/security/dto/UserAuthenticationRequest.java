package com.global.mazaad.security.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationRequest(
    @NotBlank(message = "phoneNumber is a mandatory attribute") String phoneNumber,
    @NotBlank(message = "Password is a mandatory attribute") String password) {}

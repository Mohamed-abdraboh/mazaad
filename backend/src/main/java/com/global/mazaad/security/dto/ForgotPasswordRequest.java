package com.global.mazaad.security.dto;

import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(

        @NotBlank(message = "Email is the mandatory attribute")
        String email
) {
}
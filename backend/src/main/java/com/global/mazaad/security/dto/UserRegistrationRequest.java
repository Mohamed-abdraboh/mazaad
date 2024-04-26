package com.global.mazaad.security.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
  @NotBlank private String name;

  @NotBlank(message = "phoneNumber is a mandatory attribute")
  @Pattern(regexp = "^\\+?[0-9]{1,3}\\s?[0-9]{3,14}$", message = "Invalid phone number")
  private String phoneNumber;

  @NotBlank(message = "Password is a mandatory attribute")
  @Size(min = 8)
  private String password;

  @NotNull @Email private String email;
  private String companyName;
  private String location;
}

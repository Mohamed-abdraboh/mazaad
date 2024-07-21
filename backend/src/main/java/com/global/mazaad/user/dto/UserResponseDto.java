package com.global.mazaad.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String name;
  private String phoneNumber;
  private String email;
  private String companyName;
  private String location;
}

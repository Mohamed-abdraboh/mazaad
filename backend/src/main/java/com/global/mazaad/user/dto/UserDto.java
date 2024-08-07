package com.global.mazaad.user.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String name;
  private String phoneNumber;
  private String email;
  private String password;
  private String companyName;
  private String location;
}

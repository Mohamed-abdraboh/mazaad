package com.global.mazaad.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

  private final Long userId;

  public UserNotFoundException(Long userId) {
    super(String.format("User with id = %s is not found.", userId));
    this.userId = userId;
  }
}

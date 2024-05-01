package com.global.mazaad.user.service;

import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.exception.PhoneNumberAlreadyExistException;
import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserValidator {
  private final UserRepository userRepository;

  public void validateIfPresent(String phoneNumber) {
    userRepository
        .findByPhoneNumber(phoneNumber)
        .ifPresent(
            user -> {
              throw new PhoneNumberAlreadyExistException(phoneNumber);
            });
  }
}

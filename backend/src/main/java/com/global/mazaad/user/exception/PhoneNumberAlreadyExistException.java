package com.global.mazaad.user.exception;

public class PhoneNumberAlreadyExistException extends RuntimeException {
  public PhoneNumberAlreadyExistException(String phoneNumber) {
    super(String.format("Phone Number : %s already exists", phoneNumber));
  }
}

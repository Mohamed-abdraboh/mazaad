package com.global.mazaad.common.exception;

public class NotActivatedAccountException extends RuntimeException {
  public NotActivatedAccountException(String phoneNumber) {
    super(String.format("The account with phone number: %s is not activated", phoneNumber));
  }
}

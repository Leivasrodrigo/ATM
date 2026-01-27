package com.ATM.domain.exception;

public class CardInactiveException extends RuntimeException {
  public CardInactiveException(String message) {
    super(message);
  }
}

package com.ATM.domain.exception;

public class SessionExpiredException extends RuntimeException {
  public SessionExpiredException() {
    super("Session expired");
  }
}

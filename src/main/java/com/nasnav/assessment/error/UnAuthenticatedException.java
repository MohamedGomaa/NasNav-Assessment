package com.nasnav.assessment.error;

public class UnAuthenticatedException extends RuntimeException {
  public UnAuthenticatedException(String message) {
    super(message);
  }
}

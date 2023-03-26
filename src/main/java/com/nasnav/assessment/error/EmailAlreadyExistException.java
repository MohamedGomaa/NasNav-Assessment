package com.nasnav.assessment.error;

public class EmailAlreadyExistException extends RuntimeException {

  public EmailAlreadyExistException(String message) {
    super(message);
  }
}

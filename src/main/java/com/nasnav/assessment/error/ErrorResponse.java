package com.nasnav.assessment.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Error response object
 */
@Getter
@Setter
public class ErrorResponse {

  private String errorCode;
  private String errorMessage;
  @JsonIgnore
  private int statusCode;

  public ErrorResponse(int statusCode, String errorCode, String errorMessage) {
    setStatusCode(statusCode);
    setErrorCode(errorCode);
    setErrorMessage(errorMessage);
  }
}

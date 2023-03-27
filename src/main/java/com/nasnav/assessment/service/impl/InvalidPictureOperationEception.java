package com.nasnav.assessment.service.impl;

public class InvalidPictureOperationEception extends
    RuntimeException {

  InvalidPictureOperationEception(String message){
    super(message);
  }
}

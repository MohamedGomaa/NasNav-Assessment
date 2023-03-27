package com.nasnav.assessment.enumeration;

import static com.nasnav.assessment.strings.ExceptionMessages.STATUS_NOT_FOUND;

import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureStatus {
  APPROVED("Approved"),
  REJECTED("Rejected"),
  UNPROCESSED("Unprocessed");

  private final String name;

  public static PictureStatus getStatusByName(String name){
    return Arrays.stream(PictureStatus.values()).filter(
        status -> status.getName().equalsIgnoreCase(name)
    ).findFirst().orElseThrow(()->new EntityNotFoundException(STATUS_NOT_FOUND));
  }
}

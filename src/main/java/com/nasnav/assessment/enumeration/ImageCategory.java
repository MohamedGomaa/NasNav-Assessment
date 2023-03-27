package com.nasnav.assessment.enumeration;

import static com.nasnav.assessment.strings.ExceptionMessages.CATEGORY_NOT_FOUND;

import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageCategory {
  LIVING_THING("Living thing"),
  MACHINE("Machine"),
  NATURE("Nature");

  private final String name;

  public static ImageCategory getCategoryByName(String name){
    return Arrays.stream(ImageCategory.values()).filter(
        imageCategory -> imageCategory.getName().equalsIgnoreCase(name)
    ).findFirst().orElseThrow(()->new EntityNotFoundException(CATEGORY_NOT_FOUND));
  }
}

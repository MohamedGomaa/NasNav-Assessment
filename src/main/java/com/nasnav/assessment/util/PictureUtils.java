package com.nasnav.assessment.util;

import static com.nasnav.assessment.strings.ExceptionMessages.MEDIA_TYPE_NOT_SUPPORTED;

import com.nasnav.assessment.error.ImageTypeNotSupportedException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PictureUtils {

  @Value("${allowed.image.types}")
  private String allowedTypes;

  public void validatePicture(MultipartFile image){
    List<String> allowedTypesArr = Arrays.asList(allowedTypes.split(","));
    if(!allowedTypesArr.contains(image.getContentType())){
      throw new ImageTypeNotSupportedException(MEDIA_TYPE_NOT_SUPPORTED);
    }
  }

}

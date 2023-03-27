package com.nasnav.assessment.dto.payload.request;

import static com.nasnav.assessment.strings.ExceptionMessages.PIC_CAT_REQUIRED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_DISC_REQUIRED;

import com.nasnav.assessment.enumeration.ImageCategory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageRequest {

  @NotBlank(message = PIC_DISC_REQUIRED)
  private String description;

  @NotNull(message = PIC_CAT_REQUIRED)
  private ImageCategory category;

}

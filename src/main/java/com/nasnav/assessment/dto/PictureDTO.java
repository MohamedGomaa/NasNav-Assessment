package com.nasnav.assessment.dto;

import static com.nasnav.assessment.strings.ExceptionMessages.PIC_CAT_REQUIRED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_DISC_REQUIRED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_NAME_REQUIRED;
import static com.nasnav.assessment.strings.ExceptionMessages.PIC_TYPE_REQUIRED;

import com.nasnav.assessment.enumeration.ImageCategory;
import com.nasnav.assessment.enumeration.PictureStatus;
import com.nasnav.assessment.strings.ExceptionMessages;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
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
public class PictureDTO {

  private Long id;

  @NotBlank(message = PIC_NAME_REQUIRED)
  private String name;

  @NotBlank(message = PIC_TYPE_REQUIRED)
  private String type;

  @NotBlank(message = PIC_DISC_REQUIRED)
  private String description;

  @NotNull(message = PIC_CAT_REQUIRED)
  private ImageCategory category;

  @NotBlank
  private String url;

  private PictureStatus pictureStatus;

  @Lob
  private byte[] data;
}

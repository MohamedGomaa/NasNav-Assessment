package com.nasnav.assessment.model;

import com.nasnav.assessment.enumeration.ImageCategory;
import com.nasnav.assessment.enumeration.PictureStatus;
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

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  @NotBlank
  private String name;

  @NotBlank
  private String type;

  @NotBlank
  @Column(length = 1024)
  private String description;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ImageCategory category;

  private String url;

  @NotNull
  @Enumerated(EnumType.STRING)
  private PictureStatus pictureStatus;

  @Lob
  private byte[] data;
}

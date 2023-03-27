package com.nasnav.assessment.model;

import static com.nasnav.assessment.strings.ExceptionMessages.USER_INVALID_INPUT_01;
import static com.nasnav.assessment.strings.ExceptionMessages.USER_INVALID_INPUT_02;
import static com.nasnav.assessment.strings.ExceptionMessages.USER_INVALID_INPUT_03;
import static com.nasnav.assessment.strings.ExceptionMessages.USER_INVALID_INPUT_04;

import com.nasnav.assessment.enumeration.Roles;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class Users{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank(message = USER_INVALID_INPUT_01)
  private String name;

  @NotBlank(message = USER_INVALID_INPUT_02)
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank(message = USER_INVALID_INPUT_03)
  private String password;

  @NotNull(message = USER_INVALID_INPUT_04)
  @Enumerated(EnumType.STRING)
  private Roles userRole;
}

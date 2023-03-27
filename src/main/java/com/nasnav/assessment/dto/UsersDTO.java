package com.nasnav.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nasnav.assessment.enumeration.Roles;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

  private Long id;

  @NotBlank(message = "USER_INVALID_INPUT_01")
  private String name;

  @NotBlank(message = "USER_INVALID_INPUT_02")
  @Email
  @Column(unique = true)
  private String email;

  @JsonIgnore
  @NotBlank(message = "USER_INVALID_INPUT_03")
  private String password;

  @NotBlank(message = "USER_INVALID_INPUT_04")
  private Roles userRole;
}

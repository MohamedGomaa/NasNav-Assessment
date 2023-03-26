package com.nasnav.assessment.dto.payload.request;

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
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "USER_INVALID_INPUT_01")
  private String name;

  @NotBlank(message = "USER_INVALID_INPUT_02")
  @Email(message = "USER_EMAIL_NOT_VALID")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "USER_INVALID_INPUT_03")
  private String password;

  private Roles userRole = Roles.REGULAR;
}

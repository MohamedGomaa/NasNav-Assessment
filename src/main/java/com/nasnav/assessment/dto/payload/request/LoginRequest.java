package com.nasnav.assessment.dto.payload.request;

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
public class LoginRequest {

  @NotBlank(message = "USER_EMAIL_REQUIRED")
  @Email(message = "USER_EMAIL_NOT_VALID")
  private String email;

  @NotBlank(message = "USER_PASS_REQUIRED")
  private String password;
}

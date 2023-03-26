package com.nasnav.assessment.model;

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
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotBlank(message = "Name is empty or null.")
  private String name;

  @NotBlank(message = "Name is empty or null.")
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Password is null or empty.")
  private String password;

  @NotNull(message = "Role is null or empty")
  @Enumerated(EnumType.STRING)
  private Roles userRole;
}

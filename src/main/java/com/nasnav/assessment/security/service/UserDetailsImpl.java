package com.nasnav.assessment.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.model.Users;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;
  private Long userId;
  private String name;
  private String email;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long userId, String name, String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super();
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }


  public static UserDetailsImpl build(UsersDTO user) {
    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority(user.getUserRole().name()));

    return new UserDetailsImpl(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    UserDetailsImpl user = (UserDetailsImpl) object;
    return Objects.equals(userId, user.userId);
  }
}

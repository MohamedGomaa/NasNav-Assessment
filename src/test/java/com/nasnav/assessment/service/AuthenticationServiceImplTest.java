package com.nasnav.assessment.service;

import static com.nasnav.assessment.strings.ExceptionMessages.EMAIL_ALREADY_EXIST;
import static com.nasnav.assessment.strings.ExceptionMessages.LOGIN_FAILURE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.LoginRequest;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.dto.payload.response.JwtResponse;
import com.nasnav.assessment.enumeration.Roles;
import com.nasnav.assessment.error.EmailAlreadyExistException;
import com.nasnav.assessment.error.UnAuthenticatedException;
import com.nasnav.assessment.security.JwtUtils;
import com.nasnav.assessment.security.service.UserDetailsImpl;
import com.nasnav.assessment.service.impl.AuthenticationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

  private final String mockToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGFwcC5jb20iLCJpYXQiOjE2Nzk5NDk5MDYsImV4cCI6MTY4MDAzNjMwNn0._A5ISbD6t7VofZNWL31Jo7nmZtCSC473fmmV0aVWiFgk39mnj80wu4I83Nil43atUd9qFbN7LDpl1ieP08rzww";
  @Mock
  private IUsersService usersService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private BCryptPasswordEncoder encoder;
  @Mock
  private JwtUtils jwtUtils;

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Test
  void shouldSuccessAuthenticationForValidCredentials() {
    LoginRequest loginRequest = new LoginRequest("test@mail.com", "testPass");
    when(authenticationManager.authenticate(any())).thenReturn(getAuthentication());
    when(jwtUtils.generateToken(any())).thenReturn(mockToken);

    JwtResponse response = authenticationService.authenticate(loginRequest);

    assertNotNull(response);
    assertEquals(mockToken, response.getToken());
  }

  @Test
  void shouldFailAuthenticationForInvalidCredentials() {
    LoginRequest loginRequest = new LoginRequest("test@mail.com", "wrongPass");
    when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

    UnAuthenticatedException exception = assertThrows(
        UnAuthenticatedException.class, () -> authenticationService.authenticate(loginRequest)
    );
    assertThat(exception.getMessage(), is(LOGIN_FAILURE));
  }

  @Test
  void whenEmailIsNotFoundThenRegisterSuccessfully() {
    UsersDTO usersDTO = UsersDTO.builder().id(1L).name("testName").email("testEmail")
        .password("encodedPass").userRole(Roles.REGULAR).build();

    RegisterRequest registerRequest = RegisterRequest.builder().name("testName").email("testEmail")
        .password("testPass").build();
    when(usersService.isEmailExist(any())).thenReturn(false);
    when(encoder.encode(registerRequest.getPassword())).thenReturn("encodedPass");
    when(usersService.saveUser(registerRequest)).thenReturn(usersDTO);

    UsersDTO savedUser = authenticationService.register(registerRequest);

    assertNotNull(savedUser);
    assertEquals("encodedPass", savedUser.getPassword());
  }

  @Test
  void whenEmailIsFoundThenThrowException() {
    RegisterRequest registerRequest = RegisterRequest.builder().name("testName").email("testEmail")
        .password("testPass").build();
    when(usersService.isEmailExist(any())).thenReturn(true);

    EmailAlreadyExistException exception = assertThrows(
        EmailAlreadyExistException.class, () -> authenticationService.register(registerRequest)
    );
    assertThat(exception.getMessage(), is(EMAIL_ALREADY_EXIST));
  }

  Authentication getAuthentication() {
    return new UsernamePasswordAuthenticationToken(
        new UserDetailsImpl(1l, "testName", "test@mail.com", "testPass",
            List.of(new SimpleGrantedAuthority("ADMIN"))),
        null, List.of(new SimpleGrantedAuthority("ADMIN")));
  }

}

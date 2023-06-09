package com.nasnav.assessment.controller;

import static com.nasnav.assessment.strings.ExceptionMessages.LOGIN_FAILURE;
import static com.nasnav.assessment.strings.ExceptionMessages.SYSTEM_ERROR;
import static com.nasnav.assessment.strings.Paths.AUTH_API;

import com.nasnav.assessment.dto.payload.request.LoginRequest;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.error.ApplicationException;
import com.nasnav.assessment.error.EmailAlreadyExistException;
import com.nasnav.assessment.error.MethodArgumentsNotValidException;
import com.nasnav.assessment.error.UnAuthenticatedException;
import com.nasnav.assessment.service.IAuthenticationService;
import io.swagger.annotations.ApiOperation;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AUTH_API)
@RequiredArgsConstructor
public class AuthenticationController {

  private final IAuthenticationService authenticationService;

  @ApiOperation("Log in")
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
      return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
  }

  @ApiOperation("Register")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser( @RequestBody @Valid RegisterRequest signupRequest) {
      return ResponseEntity.ok(authenticationService.register(signupRequest));
  }

}

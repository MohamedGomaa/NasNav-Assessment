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
    try{
      return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }catch(Exception e) {
      throw new UnAuthenticatedException(LOGIN_FAILURE);
    }
  }

  @ApiOperation("Register")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser( @RequestBody @Valid RegisterRequest signupRequest) {
    try{
      return ResponseEntity.ok(authenticationService.register(signupRequest));
    }catch(EntityNotFoundException e) {
      throw new EntityNotFoundException(e.getMessage());
    }catch(EmailAlreadyExistException e) {
      throw new EmailAlreadyExistException(e.getMessage());
    }catch(MethodArgumentsNotValidException e) {
      throw new MethodArgumentsNotValidException(e.getMessage());
    }catch(Exception e) {
      throw new ApplicationException(SYSTEM_ERROR);
    }
  }

}

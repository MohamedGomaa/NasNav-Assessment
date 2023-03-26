package com.nasnav.assessment.service.impl;

import static com.nasnav.assessment.strings.ExceptionMessages.EMAIL_ALREADY_EXIST;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.LoginRequest;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.dto.payload.response.JwtResponse;
import com.nasnav.assessment.enumeration.Roles;
import com.nasnav.assessment.error.EmailAlreadyExistException;
import com.nasnav.assessment.error.UnAuthenticatedException;
import com.nasnav.assessment.model.Users;
import com.nasnav.assessment.security.JwtUtils;
import com.nasnav.assessment.security.service.UserDetailsImpl;
import com.nasnav.assessment.service.IAuthenticationService;
import com.nasnav.assessment.service.IUsersService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final IUsersService usersService;
  private final JwtUtils jwtUtils;
  private final BCryptPasswordEncoder encoder;

  @Override
  public JwtResponse authenticate(LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
              loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateToken(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
      return new JwtResponse(jwt, userDetails.getUserId(), userDetails.getEmail(), roles);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new UnAuthenticatedException(e.getMessage());
    }
  }

  @Override
  public UsersDTO register(RegisterRequest signupRequest) {
    if (usersService.isEmailExist(signupRequest.getEmail())) {
      throw new EmailAlreadyExistException(EMAIL_ALREADY_EXIST);
    }
    signupRequest.setPassword(encoder.encode(signupRequest.getPassword()));
    return usersService.saveUser(signupRequest);
  }

  @PostConstruct
  private void saveAdminUser() {
    try {
      usersService.saveUser(
          RegisterRequest.builder().name("Amin").email("admin@nasnav.com")
              .password(encoder.encode("admin123")).userRole(Roles.ADMIN).build()
      );
    }catch(Exception e){
      log.error(e.getMessage());
    }
  }
}

package com.nasnav.assessment.service;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.LoginRequest;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.dto.payload.response.JwtResponse;

public interface IAuthenticationService {

  JwtResponse authenticate(LoginRequest loginRequest);

  UsersDTO register(RegisterRequest signupRequest);
}

package com.nasnav.assessment.service;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;

public interface IUsersService {

  UsersDTO getUserByEmail(String email);

  boolean isEmailExist(String email);

  UsersDTO saveUser(RegisterRequest registerRequest);
}

package com.nasnav.assessment.service;

import com.nasnav.assessment.dto.UsersDTO;

public interface IUsersService {

  UsersDTO getUserByEmail(String email);
}

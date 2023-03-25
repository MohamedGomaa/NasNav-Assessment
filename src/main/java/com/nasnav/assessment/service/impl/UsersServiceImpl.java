package com.nasnav.assessment.service.impl;

import static com.nasnav.assessment.error.ExceptionMessages.USER_NOT_FOUND;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.error.ApplicationException;
import com.nasnav.assessment.mapper.UsersMapper;
import com.nasnav.assessment.repository.UsersRepository;
import com.nasnav.assessment.service.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements IUsersService {

  private final UsersRepository usersRepository;
  private final UsersMapper usersMapper;

  @Override
  public UsersDTO getUserByEmail(String email){
    return usersMapper.toDTO(usersRepository.findByEmail(email).orElseThrow(
        () -> new ApplicationException(USER_NOT_FOUND)
    ));
  }

}

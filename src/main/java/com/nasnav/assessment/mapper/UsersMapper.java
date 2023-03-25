package com.nasnav.assessment.mapper;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

  Users toEntity(UsersDTO usersDTO);
  UsersDTO toDTO(Users users);
}

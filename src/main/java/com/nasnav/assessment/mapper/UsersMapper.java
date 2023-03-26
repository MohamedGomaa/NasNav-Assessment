package com.nasnav.assessment.mapper;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.enumeration.Roles;
import com.nasnav.assessment.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = Roles.class)
public interface UsersMapper {
  Users toEntity(UsersDTO usersDTO);
  Users toEntity(RegisterRequest registerRequest);
  UsersDTO toDTO(Users users);
  UsersDTO toDTO(RegisterRequest registerRequest);
}

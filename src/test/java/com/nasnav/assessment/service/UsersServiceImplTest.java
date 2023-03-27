package com.nasnav.assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.dto.payload.request.RegisterRequest;
import com.nasnav.assessment.enumeration.Roles;
import com.nasnav.assessment.mapper.UsersMapper;
import com.nasnav.assessment.model.Users;
import com.nasnav.assessment.repository.UsersRepository;
import com.nasnav.assessment.service.impl.UsersServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

  @Mock
  UsersRepository usersRepoMock;

  @Mock
  UsersMapper usersMapperMock;

  @InjectMocks
  UsersServiceImpl usersService;

  private static Users userMock;
  private static UsersDTO usersDTOMock;

  @BeforeAll
  static void setup() {
    userMock = Users.builder().id(11l).name("testUser").email("test@mail.com")
        .userRole(Roles.REGULAR).password("password").build();
    usersDTOMock = UsersDTO.builder().id(11l).name("testUser").email("test@mail.com")
        .userRole(Roles.REGULAR).password("password").build();
  }

  @Test
  void shouldGetUserByEmailWhenSucceed() {
    when(usersMapperMock.toDTO(any(Users.class))).thenReturn(usersDTOMock);
    when(usersRepoMock.findByEmail(any())).thenReturn(Optional.ofNullable(userMock));

    UsersDTO usersDTO = usersService.getUserByEmail(userMock.getEmail());

    assertNotNull(usersDTO);
    assertEquals(userMock.getId(), usersDTO.getId());
  }

  @Test
  void shouldCheckUserByEmailWhenSucceed() {
    when(usersRepoMock.existsByEmail(any())).thenReturn(true);

    assertTrue(usersService.isEmailExist(userMock.getEmail()));
  }

  @Test
  void shouldSaveUserSuccessfully() {
    RegisterRequest registerRequest = RegisterRequest.builder().email("test@mail.com")
        .password("password").name("testUser").build();

    when(usersRepoMock.save(any(Users.class))).thenReturn(userMock);
    when(usersMapperMock.toDTO(any(Users.class))).thenReturn(usersDTOMock);
    when(usersMapperMock.toEntity(any(RegisterRequest.class))).thenReturn(userMock);

    UsersDTO usersDTO = usersService.saveUser(registerRequest);

    assertNotNull(usersDTO.getId());
    assertNotNull(usersDTO.getUserRole());
    assertEquals(Roles.REGULAR, usersDTO.getUserRole());
  }

}

package com.nasnav.assessment.security.service;

import com.nasnav.assessment.dto.UsersDTO;
import com.nasnav.assessment.service.IUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final IUsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersDTO user = usersService.getUserByEmail(username);
        return UserDetailsImpl.build(user);
    }
}

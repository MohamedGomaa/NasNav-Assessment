package com.nasnav.assessment.dto.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer ";
    private Long id;
    private String email;
    private List<String> roles;


    public JwtResponse(String accessToken, Long id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

}

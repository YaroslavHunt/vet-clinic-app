package com.vetclinic.vetclinicapp.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDTO {

    private String username;

    private String password;

    private String role;

}

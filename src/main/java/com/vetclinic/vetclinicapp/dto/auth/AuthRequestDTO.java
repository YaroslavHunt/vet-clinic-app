package com.vetclinic.vetclinicapp.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
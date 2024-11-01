package com.vetclinic.vetclinicapp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetDTO {
    private Long id;

    @NotBlank(message = "The name field is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "The email field is required")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email must follow the format: user@example.com"
    )
    private String email;

    @NotBlank(message = "The phone field is required")
    private String phone;

    @NotBlank(message = "The specialization field is required")
    private String specialization;

    @NotNull(message = "The experience field is required")
    @Min(value = 0, message = "Experience must be positive")
    private Integer experience;

    private List<AppointmentDTO> appointments;
}

package com.vetclinic.vetclinicapp.dto;

import com.vetclinic.vetclinicapp.models.Appointment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

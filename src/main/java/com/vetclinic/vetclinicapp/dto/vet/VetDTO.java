package com.vetclinic.vetclinicapp.dto.vet;

import com.vetclinic.vetclinicapp.dto.appointment.AnyAppointmentDTO;
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
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must follow the format: user@example.com"
    )
    private String email;

    @NotBlank(message = "The phone field is required")
    @Pattern(
            regexp = "^\\+?\\d{10,15}$\n",
            message = "Phone number must be in the format: +1234567890 or 1234567890"
    )
    private String phone;

    @NotBlank(message = "The specialization field is required")
    private String specialization;

    @NotNull(message = "The experience field is required")
    @Min(value = 0, message = "Experience must be positive")
    @Max(value = 70, message = "Experience can't be greater than 70 year")
    private Integer experience;

    private List<AnyAppointmentDTO> appointments;
}
package com.vetclinic.vetclinicapp.dto.vet;

import com.vetclinic.vetclinicapp.constants.Constants;
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
            regexp = Constants.EMAIL_REGEX,
            message = "Email must follow the format: user@example.com"
    )
    private String email;

    @NotBlank(message = "The phone field is required")
    @Pattern(
            regexp = Constants.PHONE_NUMBER_REGEX,
            message = "Invalid phone number format. Valid formats are: " + Constants.PHONE_NUMBER_FORMATS
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

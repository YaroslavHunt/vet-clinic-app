package com.vetclinic.vetclinicapp.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotBlank(message = "The pet id field is required")
    private Long petId;

    @NotBlank(message = "The vet id field is required")
    private Long vetId;

    private LocalDateTime appointmentDate;

    private Long treatmentId;
}

package com.vetclinic.vetclinicapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotBlank(message = "The pet field is required")
    private PetDTO pet;

    @NotBlank(message = "The vet field is required")
    private VetDTO vet;

    private LocalDateTime appointmentDate;

    private TreatmentDTO treatment;

}

package com.vetclinic.vetclinicapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDTO {
    private Long id;

    private AppointmentDTO appointment;

    @NotBlank(message = "The pet id field is required")
    private Long petId;

    private String description;

    private Double cost;

    private LocalDateTime treatmentDate;
}

package com.vetclinic.vetclinicapp.dto.treatment;

import com.vetclinic.vetclinicapp.dto.appointment.AnyAppointmentDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDTO {
    private Long id;

    private AnyAppointmentDTO appointment;

    @NotNull(message = "The pet id field is required")
    private Long petId;

    @NotBlank(message = "The description field is required")
    private String description;

    @Positive(message = "Cost must be a positive number")
    private Double cost;

    private LocalDateTime treatmentDate;
}

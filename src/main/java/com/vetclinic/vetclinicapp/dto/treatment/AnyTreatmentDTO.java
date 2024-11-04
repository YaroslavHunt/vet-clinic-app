package com.vetclinic.vetclinicapp.dto.treatment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyTreatmentDTO {
    private Long id;

    @FutureOrPresent(message = "Treatment date must be in the present or future")
    private LocalDateTime treatmentDate;

    @NotBlank(message = "The description field is required")
    private String description;
}

package com.vetclinic.vetclinicapp.dto.appointment;

import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotNull(message = "The pet id field is required")
    private Long petId;

    @NotNull(message = "The vet id field is required")
    private Long vetId;

    private LocalDateTime appointmentDate;

    private AnyTreatmentDTO treatment;
}

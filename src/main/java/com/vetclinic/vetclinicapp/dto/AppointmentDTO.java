package com.vetclinic.vetclinicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;

//    @NotBlank(message = "The pet id field is required")
//    private Long petId;
//
//    @NotBlank(message = "The vet id field is required")
//    private Long vetId;

    private LocalDateTime appointmentDate;

//    private Long treatmentId;

}

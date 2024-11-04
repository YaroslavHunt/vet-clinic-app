package com.vetclinic.vetclinicapp.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyAppointmentDTO {

    private Long id;

    private LocalDateTime appointmentDate;

}

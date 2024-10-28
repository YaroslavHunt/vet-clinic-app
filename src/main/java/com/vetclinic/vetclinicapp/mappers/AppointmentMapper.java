package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.AppointmentDTO;
import com.vetclinic.vetclinicapp.models.Appointment;
import org.mapstruct.Mapper;

@Mapper
public interface AppointmentMapper {
    Appointment toEntity(AppointmentDTO dto);
    AppointmentDTO toDTO(Appointment appointment);
}

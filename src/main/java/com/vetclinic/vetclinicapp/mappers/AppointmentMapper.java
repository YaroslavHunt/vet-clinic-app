package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.models.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AppointmentMapper {
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "vetId", source = "vet.id")
    AppointmentDTO toDTO(Appointment appointment);

    @Mapping(target = "pet.id", source = "dto.petId")
    @Mapping(target = "vet.id", source = "dto.vetId")
    Appointment toEntity(AppointmentDTO dto);


    default List<AppointmentDTO> toDTOs(Collection<Appointment> appointments) {
        return appointments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

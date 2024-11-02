package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.mappers.AppointmentMapper;
import com.vetclinic.vetclinicapp.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentDTO> findFilteredAppointments(Long vetId, Long petId, LocalDate date) {
        if (vetId != null && petId != null) {
            return appointmentMapper.toDTOs(appointmentRepository.findAllByVetIdAndPetId(vetId, petId));
        } else if (vetId != null) {
            return appointmentMapper.toDTOs(appointmentRepository.findAllByVetId(vetId));
        } else if (petId != null) {
            return appointmentMapper.toDTOs(appointmentRepository.findAllByPetId(petId));
        } else if (date != null) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
            return appointmentMapper.toDTOs(appointmentRepository.findAllByAppointmentDate(startOfDay, endOfDay));
        } else {
            return appointmentMapper.toDTOs(appointmentRepository.findAll());
        }
    }


}

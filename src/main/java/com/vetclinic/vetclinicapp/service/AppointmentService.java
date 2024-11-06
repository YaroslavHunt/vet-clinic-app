package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.AppointmentMapper;
import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.models.Vet;
import com.vetclinic.vetclinicapp.repository.AppointmentRepository;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import com.vetclinic.vetclinicapp.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
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

    public AppointmentDTO findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Appointment with id %d not found", id),
                                HttpStatus.NOT_FOUND));
        return appointmentMapper.toDTO(appointment);
    }

    public AppointmentDTO addAppointment(AppointmentDTO dto) {
        if (dto.getTreatment() != null) {
            throw new GlobalExceptionHandler.CustomException(
                    "Treatment field should not be provided at this stage.",
                    HttpStatus.BAD_REQUEST);
        }

        if (!vetRepository.existsById(dto.getVetId())) {
            throw new GlobalExceptionHandler.CustomException(
                    String.format("Vet with id %d not found", dto.getVetId()),
                    HttpStatus.BAD_REQUEST);
        }

        if (!petRepository.existsById(dto.getPetId())) {
            throw new GlobalExceptionHandler.CustomException(
                    String.format("Pet with id %d not found", dto.getPetId()),
                    HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = appointmentMapper.toEntity(dto);
        appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentDTO changeAppointment(Long id, AppointmentDTO newAppData) {
        Appointment existingApp = appointmentRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler
                        .CustomException(
                        String.format("Appointment with id %d not found", id),
                        HttpStatus.NOT_FOUND));

        if (newAppData.getPetId() != null) {
            throw new GlobalExceptionHandler.CustomException(
                    "Pet field should not be provided at this stage.",
                    HttpStatus.BAD_REQUEST);
        }

        if (newAppData.getTreatment() != null) {
            throw new GlobalExceptionHandler.CustomException(
                    "Treatment field should not be provided at this stage.",
                    HttpStatus.BAD_REQUEST);
        }

        Vet vet = vetRepository.findById(newAppData.getVetId()).orElseThrow(
                () -> new GlobalExceptionHandler.CustomException(
                        String.format("Vet with id %d not found", newAppData.getVetId()),
                        HttpStatus.BAD_REQUEST));

        existingApp.setVet(vet);
        existingApp.setAppointmentDate(newAppData.getAppointmentDate());

        appointmentRepository.save(existingApp);
        return appointmentMapper.toDTO(existingApp);
    }

    @Transactional
    public void addAppointmentToVet(Long vetId, Long appointmentId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Vet with id %d not found", vetId),
                                HttpStatus.NOT_FOUND));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Appointment with id %d not found", appointmentId),
                                HttpStatus.NOT_FOUND));

        vet.getAppointments().add(appointment);
        appointment.setVet(vet);
        vetRepository.save(vet);
    }

    @Transactional
    public void removeAppointmentFromVet(Long vetId, Long appointmentId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Vet with id %d not found", vetId),
                                HttpStatus.NOT_FOUND));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Appointment with id %d not found", appointmentId),
                                HttpStatus.NOT_FOUND));

        vet.getAppointments().remove(appointment);
        appointment.setVet(null);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment app = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Appointment with id %d not found", id),
                                HttpStatus.NOT_FOUND));

        if (app.getTreatment() != null) {
            throw new GlobalExceptionHandler.CustomException(
                    "Cannot delete this appointment, as it is associated with a treatment.",
                    HttpStatus.CONFLICT);
        }

        appointmentRepository.delete(app);
    }

}

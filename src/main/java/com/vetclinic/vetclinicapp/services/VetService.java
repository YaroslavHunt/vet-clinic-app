package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.dto.VetDTO;
import com.vetclinic.vetclinicapp.exceptions.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mappers.VetMapper;
import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.models.Vet;
import com.vetclinic.vetclinicapp.repositories.AppointmentRepository;
import com.vetclinic.vetclinicapp.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;
    private final AppointmentRepository appointmentRepository;
    private final VetMapper vetMapper;

    public List<VetDTO> findAll() {
        return vetRepository.findAll()
                .stream()
                .map(vetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<VetDTO> findAllByExperienceGraterThan(Integer exp) {
        return vetRepository.findAllByExperienceGreaterThan(exp)
                .stream()
                .map(vetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VetDTO findById(Long id) {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Vet with id %d not found", id)));
        return vetMapper.toDTO(vet);
    }

    @SneakyThrows
    public VetDTO addVet(VetDTO vetDTO) {
        if (vetRepository.existsByEmail(vetDTO.getEmail())) {
            throw new SQLIntegrityConstraintViolationException("Email already exists: " + vetDTO.getEmail());
        }
        if (vetRepository.existsByPhone(vetDTO.getPhone())) {
            throw new SQLIntegrityConstraintViolationException("Phone number already exists: " + vetDTO.getPhone());
        }

        Vet vet = vetMapper.toEntity(vetDTO);
        vetRepository.save(vet);
        return vetMapper.toDTO(vet);
    }

    @Transactional
    public VetDTO updateVet(Long id, VetDTO newVetData) {
        Vet existingVet = vetRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler
                        .ResourceNotFoundException(
                                String.format("Vet with id %d not found", id)));

        Optional.ofNullable(newVetData.getName()).ifPresent(existingVet::setName);
        Optional.ofNullable(newVetData.getEmail()).ifPresent(existingVet::setEmail);
        Optional.ofNullable(newVetData.getPhone()).ifPresent(existingVet::setPhone);
        Optional.ofNullable(newVetData.getSpecialization()).ifPresent(existingVet::setSpecialization);
        Optional.ofNullable(newVetData.getExperience()).ifPresent(existingVet::setExperience);

        vetRepository.save(existingVet);
        return vetMapper.toDTO(existingVet);
    }

    @Transactional
    public void addAppointmentToVet(Long vetId, Long appointmentId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Vet with id %d not found", vetId)));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Appointment with id %d not found", appointmentId)));

        vet.getAppointments().add(appointment);
        appointment.setVet(vet);
        vetRepository.save(vet);
    }

    @Transactional
    public void removeAppointmentFromVet(Long vetId, Long appointmentId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Vet with id %d not found", vetId)));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Appointment with id %d not found", appointmentId)));

        if (vet.getAppointments().remove(appointment)) {
            appointment.setVet(null);
            vetRepository.save(vet);
        }
    }

    @Transactional
    public void deleteVet(Long id) {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .ResourceNotFoundException(
                                        String.format("Vet with id %d not found", id)));
        vetRepository.delete(vet);
    }
}

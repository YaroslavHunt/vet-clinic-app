package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.exceptions.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mappers.TreatmentMapper;
import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.models.Pet;
import com.vetclinic.vetclinicapp.models.Treatment;
import com.vetclinic.vetclinicapp.repositories.AppointmentRepository;
import com.vetclinic.vetclinicapp.repositories.PetRepository;
import com.vetclinic.vetclinicapp.repositories.TreatmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final TreatmentMapper treatmentMapper;

    public List<TreatmentDTO> findByDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);
        return findTreatmentsByDateParams(startDate, endDate);
    }

    public List<TreatmentDTO> findByMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return findTreatmentsByDateParams(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    public List<TreatmentDTO> findByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return findTreatmentsByDateParams(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    public List<TreatmentDTO> findByPetId(Long petId) {
        return treatmentRepository.findByPetId(petId).stream()
                .map(treatmentMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<TreatmentDTO> findAll() {
        return treatmentRepository.findAll().stream()
                .map(treatmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<TreatmentDTO> findTreatmentsByDateParams(LocalDateTime startDate, LocalDateTime endDate) {
        List<Treatment> treatments = treatmentRepository.findByTreatmentDateBetween(startDate, endDate);
        return treatments.stream()
                .map(treatmentMapper::toDTO)
                .collect(Collectors.toList());
    }


    public TreatmentDTO findTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Treatment with id %d not found", id),
                                HttpStatus.NOT_FOUND));
        return treatmentMapper.toDTO(treatment);
    }

    public AnyTreatmentDTO addTreatment(TreatmentDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() ->
                        new GlobalExceptionHandler.CustomException(
                                String.format("Pet with id %d not found", dto.getPetId()),
                                HttpStatus.NOT_FOUND));

        Treatment treatment = new Treatment();
        treatment.setCost(dto.getCost());
        treatment.setDescription(dto.getDescription());
        treatment.setPet(pet);
        treatment.setTreatmentDate(dto.getTreatmentDate());

        if (dto.getAppointmentId() != null) {
            if (appointmentRepository.existsById(dto.getAppointmentId())) {
                throw new GlobalExceptionHandler.CustomException(
                        String.format(
                                "Treatment for appointment with id %d already exists. " +
                                        "Please use a different appointment or update the existing treatment.",
                                dto.getAppointmentId()),
                        HttpStatus.BAD_REQUEST);
            }

            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() ->
                            new GlobalExceptionHandler.CustomException(
                                    String.format("Appointment with id %d not found", dto.getAppointmentId()),
                                    HttpStatus.NOT_FOUND));
            treatment.setAppointment(appointment);
        }

        treatmentRepository.save(treatment);
        return treatmentMapper.toAnyDTO(treatment);
    }

    @Transactional
    public void addTreatmentToAppointment(Long appId, Long treatmentId) {
        Appointment appointment = appointmentRepository.findById(appId).orElseThrow(
                () -> new GlobalExceptionHandler
                        .CustomException(
                        String.format("Appointment with id %d not found", appId),
                        HttpStatus.NOT_FOUND));

        Treatment treatment = treatmentRepository.findById(treatmentId).orElseThrow(
                () -> new GlobalExceptionHandler
                        .CustomException(
                        String.format("Treatment with id %d not found", treatmentId),
                        HttpStatus.NOT_FOUND));

        if (appointmentRepository.existsById(appId)) {
            throw new GlobalExceptionHandler.CustomException(
                    String.format(
                            "Treatment for appointment with id %d already exists. " +
                                    "Please use a different appointment or update the existing treatment.",
                            appId),
                    HttpStatus.BAD_REQUEST);
        }

        appointment.setTreatment(treatment);
        treatment.setAppointment(appointment);
        appointmentRepository.save(appointment);
        treatmentRepository.save(treatment);
    }

    @Transactional
    public void deleteTreatment(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Treatment with id %d not found", id),
                                HttpStatus.NOT_FOUND));

        treatmentRepository.delete(treatment);
    }
}
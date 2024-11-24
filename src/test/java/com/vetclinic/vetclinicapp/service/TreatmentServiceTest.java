package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.entity.Appointment;
import com.vetclinic.vetclinicapp.entity.Pet;
import com.vetclinic.vetclinicapp.entity.Treatment;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.TreatmentMapper;
import com.vetclinic.vetclinicapp.repository.AppointmentRepository;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import com.vetclinic.vetclinicapp.repository.TreatmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreatmentServiceTest {

    @Mock
    private TreatmentRepository treatmentRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PetRepository petRepository;

    private final TreatmentMapper treatmentMapper = Mappers.getMapper(TreatmentMapper.class);

    private TreatmentService treatmentService;

    @BeforeEach
    void setUp() {
        treatmentService = new TreatmentService(
                treatmentRepository,
                appointmentRepository,
                petRepository,
                treatmentMapper);
    }

    @Test
    void findTreatmentById_shouldThrowExceptionIfTreatmentNotFound() {
        Long treatmentId = 1L;
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> treatmentService.findTreatmentById(treatmentId));
    }

    @Test
    void addTreatment_shouldThrowExceptionIfPetNotFound() {
        TreatmentDTO dto = new TreatmentDTO();
        dto.setPetId(1L);

        when(petRepository.findById(dto.getPetId())).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> treatmentService.addTreatment(dto));
    }

    @Test
    void addTreatment_shouldThrowExceptionIfAppointmentAlreadyExists() {
        TreatmentDTO dto = new TreatmentDTO();
        dto.setPetId(1L);
        dto.setAppointmentId(1L);

        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(dto.getPetId())).thenReturn(Optional.of(pet));
        when(appointmentRepository.existsById(dto.getAppointmentId())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> treatmentService.addTreatment(dto));
    }

    @Test
    void addTreatment_shouldAddTreatmentSuccessfully() {
        TreatmentDTO dto = new TreatmentDTO();
        dto.setPetId(1L);
        dto.setCost(100.0);
        dto.setDescription("Treatment description");

        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(dto.getPetId())).thenReturn(Optional.of(pet));
        when(treatmentRepository.save(any(Treatment.class))).thenReturn(new Treatment());

        AnyTreatmentDTO result = treatmentService.addTreatment(dto);

        assertNotNull(result);
        verify(treatmentRepository, times(1)).save(any(Treatment.class));
    }

    @Test
    void addTreatmentToAppointment_shouldThrowExceptionIfAppointmentNotFound() {
        Long appId = 1L;
        Long treatmentId = 1L;

        when(appointmentRepository.findById(appId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> treatmentService.addTreatmentToAppointment(appId, treatmentId));
    }

    @Test
    void addTreatmentToAppointment_shouldAddTreatmentToAppointmentSuccessfully() {
        Long appId = 1L;
        Long treatmentId = 1L;

        Appointment appointment = new Appointment();
        appointment.setId(appId);

        Treatment treatment = new Treatment();
        treatment.setId(treatmentId);

        when(appointmentRepository.findById(appId)).thenReturn(Optional.of(appointment));
        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(treatment));

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(treatmentRepository.save(any(Treatment.class))).thenReturn(treatment);

        treatmentService.addTreatmentToAppointment(appId, treatmentId);

        verify(appointmentRepository, times(1)).save(appointment);
        verify(treatmentRepository, times(1)).save(treatment);
    }

    @Test
    void deleteTreatment_shouldThrowExceptionIfTreatmentNotFound() {
        Long treatmentId = 1L;

        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> treatmentService.deleteTreatment(treatmentId));
    }

    @Test
    void deleteTreatment_shouldDeleteTreatmentSuccessfully() {
        Long treatmentId = 1L;
        Treatment treatment = new Treatment();
        treatment.setId(treatmentId);

        when(treatmentRepository.findById(treatmentId)).thenReturn(Optional.of(treatment));
        doNothing().when(treatmentRepository).delete(any(Treatment.class));

        treatmentService.deleteTreatment(treatmentId);

        verify(treatmentRepository, times(1)).delete(treatment);
    }
}

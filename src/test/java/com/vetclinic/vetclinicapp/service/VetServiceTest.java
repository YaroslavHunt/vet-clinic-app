package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.vet.VetDTO;
import com.vetclinic.vetclinicapp.entity.Appointment;
import com.vetclinic.vetclinicapp.entity.Vet;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.VetMapper;
import com.vetclinic.vetclinicapp.repository.AppointmentRepository;
import com.vetclinic.vetclinicapp.repository.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetServiceTest {

    @Mock
    private VetRepository vetRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Spy
    private final VetMapper vetMapper = Mappers.getMapper(VetMapper.class);

    private VetService vetService;

    @BeforeEach
    void setUp() {
        vetService = new VetService(
                vetRepository,
                appointmentRepository,
                vetMapper);
    }

    @Test
    void findById_shouldThrowExceptionIfNotFound() {
        Long vetId = 1L;
        when(vetRepository.findById(vetId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.findById(vetId));
    }

    @Test
    void addVet_shouldThrowExceptionIfEmailAlreadyExists() {
        VetDTO vetDTO = new VetDTO();
        vetDTO.setEmail("test@example.com");

        when(vetRepository.existsByEmail(vetDTO.getEmail())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.addVet(vetDTO));
    }

    @Test
    void addVet_shouldThrowExceptionIfPhoneAlreadyExists() {
        VetDTO vetDTO = new VetDTO();
        vetDTO.setPhone("123456789");

        when(vetRepository.existsByPhone(vetDTO.getPhone())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.addVet(vetDTO));
    }

    @Test
    void updateVet_shouldThrowExceptionIfVetNotFound() {
        Long vetId = 999L;
        VetDTO vetDTO = new VetDTO();
        vetDTO.setName("Updated Name");

        when(vetRepository.findById(vetId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.updateVet(vetId, vetDTO));
    }

    @Test
    void updateVet_shouldUpdateVetDetails() {
        Long vetId = 1L;
        VetDTO vetDTO = new VetDTO();
        vetDTO.setName("Updated Name");

        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setName("Old Name");

        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        when(vetRepository.save(vet)).thenReturn(vet);

        VetDTO updatedVet = vetService.updateVet(vetId, vetDTO);

        assertEquals("Updated Name", updatedVet.getName());
    }

    @Test
    void deleteVet_shouldThrowExceptionIfVetNotFound() {
        Long vetId = 999L;

        when(vetRepository.findById(vetId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.deleteVet(vetId));
    }

    @Test
    void deleteVet_shouldThrowExceptionIfVetHasAppointments() {
        Long vetId = 1L;
        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setAppointments(Collections.singletonList(new Appointment()));

        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.deleteVet(vetId));
    }

    @Test
    void deleteVet_shouldDeleteVet() {
        Long vetId = 1L;
        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setAppointments(Collections.emptyList());

        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        doNothing().when(vetRepository).delete(vet);

        vetService.deleteVet(vetId);

        verify(vetRepository, times(1)).delete(vet);
    }

    @Test
    void deleteAllAppointmentsFromVet_shouldThrowExceptionIfVetNotFound() {
        Long vetId = 999L;

        when(vetRepository.findById(vetId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> vetService.deleteAllAppointmentsFromVet(vetId));
    }

    @Test
    void deleteAllAppointmentsFromVet_shouldDeleteAppointments() {
        Long vetId = 1L;
        Vet vet = new Vet();
        vet.setId(vetId);
        Appointment appointment = new Appointment();
        appointment.setVet(vet);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        vet.setAppointments(appointments);

        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        when(vetRepository.save(vet)).thenReturn(vet);

        vetService.deleteAllAppointmentsFromVet(vetId);

        assertEquals(0, vet.getAppointments().size());

        assertNull(appointment.getVet());

        verify(vetRepository).save(vet);
    }

}

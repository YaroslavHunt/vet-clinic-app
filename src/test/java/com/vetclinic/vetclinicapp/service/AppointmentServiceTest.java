package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import com.vetclinic.vetclinicapp.entity.Appointment;
import com.vetclinic.vetclinicapp.entity.Treatment;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.AppointmentMapper;
import com.vetclinic.vetclinicapp.repository.AppointmentRepository;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import com.vetclinic.vetclinicapp.repository.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    private final AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);

    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        appointmentService = new AppointmentService(
                appointmentRepository,
                petRepository,
                vetRepository,
                appointmentMapper
        );

    }

    @Test
    void findAppointmentById_shouldThrowExceptionIfNotFound() {
        Long appointmentId = 1L;
        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.findAppointmentById(appointmentId));
    }

    @Test
    void addAppointment_shouldFailOnAppDtoTreatment() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setTreatment(new AnyTreatmentDTO());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.addAppointment(dto));
    }

    @Test
    void addAppointment_shouldThrowExceptionIfVetNotFound() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setVetId(999L);
        dto.setPetId(1L);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.addAppointment(dto));
    }

    @Test
    void addAppointment_shouldThrowExceptionIfPetNotFound() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setVetId(1L);
        dto.setPetId(999L);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.addAppointment(dto));
    }

    @Test
    void changeAppointment_shouldThrowExceptionIfAppointmentNotFound() {
        Long appointmentId = 999L;
        AppointmentDTO newAppData = new AppointmentDTO();

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.changeAppointment(appointmentId, newAppData));
    }

    @Test
    void changeAppointment_shouldFailOnPetField() {
        AppointmentDTO newAppData = new AppointmentDTO();
        newAppData.setPetId(1L);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.changeAppointment(1L, newAppData));
    }

    @Test
    void changeAppointment_shouldFailOnTreatmentField() {
        AppointmentDTO newAppData = new AppointmentDTO();
        newAppData.setTreatment(new AnyTreatmentDTO());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.changeAppointment(1L, newAppData));
    }

    @Test
    void addAppointmentToVet_shouldThrowExceptionIfVetNotFound() {
        Long vetId = 999L;
        Long appointmentId = 1L;

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.addAppointmentToVet(vetId, appointmentId));
    }

    @Test
    void addAppointmentToVet_shouldThrowExceptionIfAppointmentNotFound() {
        Long vetId = 1L;
        Long appointmentId = 999L;

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.addAppointmentToVet(vetId, appointmentId));
    }

    @Test
    void removeAppointmentFromVet_shouldThrowExceptionIfVetNotFound() {
        Long vetId = 999L;
        Long appointmentId = 1L;

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.removeAppointmentFromVet(vetId, appointmentId));
    }

    @Test
    void removeAppointmentFromVet_shouldThrowExceptionIfAppointmentNotFound() {
        Long vetId = 1L;
        Long appointmentId = 999L;

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.removeAppointmentFromVet(vetId, appointmentId));
    }

    @Test
    void deleteAppointment_shouldThrowExceptionIfAppointmentNotFound() {
        Long appointmentId = 999L;

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.deleteAppointment(appointmentId));
    }

    @Test
    void deleteAppointment_shouldFailIfTreatmentExists() {
        Appointment appointment = new Appointment();
        appointment.setTreatment(new Treatment());
        appointmentRepository.save(appointment);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> appointmentService.deleteAppointment(appointment.getId()));
    }
}
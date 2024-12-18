package com.vetclinic.vetclinicapp.controller;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Secured("ADMIN")
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll(
            @RequestParam(required = false) Long vetId,
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<AppointmentDTO> appointments = appointmentService.findFilteredAppointments(vetId, petId, date);
        return appointments.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long id) {
        AppointmentDTO dto = appointmentService.findAppointmentById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/save")
    public ResponseEntity<AppointmentDTO> add(@RequestBody @Valid AppointmentDTO dto) {
        AppointmentDTO createdAppointment = appointmentService.addAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<AppointmentDTO> change(
            @RequestBody @Valid AppointmentDTO appointment,
            @PathVariable Long id
    ){
        AppointmentDTO dto = appointmentService.changeAppointment(id, appointment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
    }

    @PostMapping("/vet={vetId}")
    public ResponseEntity<Void> addAppointmentToVet(
            @PathVariable Long vetId,
            @RequestParam(name = "add_appointment") Long appointmentId) {
        appointmentService.addAppointmentToVet(vetId, appointmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Secured("ADMIN")
    @DeleteMapping("/vet={vetId}")
    public ResponseEntity<Void> removeAppointmentFromVet(
            @PathVariable Long vetId,
            @RequestParam(name = "remove_appointment") Long appointmentId) {
        appointmentService.removeAppointmentFromVet(vetId, appointmentId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

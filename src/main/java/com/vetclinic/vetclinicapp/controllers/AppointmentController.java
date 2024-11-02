package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    public final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll(
            @RequestParam(required = false) Long vetId,
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AppointmentDTO> appointments = appointmentService.findFilteredAppointments(vetId, petId, date);
        return appointments.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Appointment appointment) {}

    @PatchMapping("/change/{id}")
    public ResponseEntity<AppointmentDTO> change(@RequestBody AppointmentDTO appointment, @PathVariable Long id){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {}

}

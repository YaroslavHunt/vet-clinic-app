package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.AppointmentDTO;
import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    public final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Appointment appointment) {}

    @PatchMapping("/change/{id}")
    public ResponseEntity<AppointmentDTO> change(@RequestBody Appointment appointment, @PathVariable Long id){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {}

}

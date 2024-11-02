package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.vet.VetDTO;
import com.vetclinic.vetclinicapp.services.VetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;

    @GetMapping
    public ResponseEntity<List<VetDTO>> getVets(@RequestParam(required = false) Integer exp) {
        List<VetDTO> vets;
        if (exp == null) {
            vets = vetService.findAll();
        } else {
            vets = vetService.findAllByExperienceGraterThan(exp);
        }
        return vets.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(vets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetDTO> getVet(@PathVariable Long id) {
        VetDTO vet = vetService.findById(id);
        return ResponseEntity.ok(vet);
    }

    @PostMapping("/{vetId}")
    public ResponseEntity<Void> addAppointment(
            @PathVariable Long vetId,
            @RequestParam(name = "add_appointment") Long appointmentId) {
        vetService.addAppointmentToVet(vetId, appointmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{vetId}")
    public ResponseEntity<Void> removeAppointment(
            @PathVariable Long vetId,
            @RequestParam(name = "remove_appointment") Long appointmentId) {
        vetService.removeAppointmentFromVet(vetId, appointmentId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{id}/appointments")
    public ResponseEntity<Void> deleteAllAppointments(@PathVariable Long id) {
        vetService.deleteAllAppointmentsFromVet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/save")
    public ResponseEntity<VetDTO> add(@RequestBody @Valid VetDTO vetDTO) {
        VetDTO createdVet = vetService.addVet(vetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVet);
    }


    @PatchMapping("/change/{id}")
    public ResponseEntity<VetDTO> change(@RequestBody @Valid VetDTO vetDTO, @PathVariable Long id) {
        VetDTO updatedVet = vetService.updateVet(id, vetDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedVet);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vetService.deleteVet(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}

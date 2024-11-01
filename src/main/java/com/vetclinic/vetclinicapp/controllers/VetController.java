package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.VetDTO;
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
    // {{host}}/vets           - return all vets
    // {{host}}/vets?exp={int} - return vets with experience grater than request value
    public ResponseEntity<List<VetDTO>> getVets(@RequestParam(required = false) Integer exp) {
        List<VetDTO> vets;
        if (exp == null) {
            vets = vetService.findAll();
        } else {
            vets = vetService.findAllByExperienceGraterThan(exp);
        }
        if (vets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/{id}")
    // {{host}}/vets/{vetId}   - return vet by ID
    public ResponseEntity<VetDTO> getVet(@PathVariable Long id) {
        VetDTO vet = vetService.findById(id);
        return ResponseEntity.ok(vet);
    }

    @PostMapping("/{vetId}")
    // {{host}}/vets/{vetId}?add_appointment={appointmentId} - add appointment for vet by vet ID & appointment ID
    public ResponseEntity<Void> addAppointment(
            @PathVariable Long vetId,
            @RequestParam(name = "add_appointment") Long appointmentId) {
        vetService.addAppointmentToVet(vetId, appointmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{vetId}")
    // {{host}}/vets/{vetId}?remove_appointment={appointmentId} - remove appointment for vet by vet ID & appointment ID
    public ResponseEntity<Void> removeAppointment(
            @PathVariable Long vetId,
            @RequestParam(name = "remove_appointment") Long appointmentId) {
        vetService.removeAppointmentFromVet(vetId, appointmentId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/save")
    // {{host}}/vets/save + (POST body.json) - add new vet
    public ResponseEntity<VetDTO> add(@RequestBody @Valid VetDTO vetDTO) {
        VetDTO createdVet = vetService.addVet(vetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVet);
    }


    @PatchMapping("/change/{id}")
    // {{host}}/vets/change/{vetId} + (PATCH body.json) - change vet data
    public ResponseEntity<VetDTO> change(@RequestBody @Valid VetDTO vetDTO, @PathVariable Long id) {
        VetDTO updatedVet = vetService.updateVet(id, vetDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedVet);
    }


    @DeleteMapping("/delete/{id}")
    // {{host}}/vets/delete/{vetId} - delete vet by ID
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vetService.deleteVet(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}

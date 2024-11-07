package com.vetclinic.vetclinicapp.controller;

import com.vetclinic.vetclinicapp.dto.vet.VetDTO;
import com.vetclinic.vetclinicapp.service.VetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ADMIN")
    @PostMapping("/save")
    public ResponseEntity<VetDTO> add(@RequestBody @Valid VetDTO vetDTO) {
        VetDTO createdVet = vetService.addVet(vetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVet);
    }

    @Secured("ADMIN")
    @PatchMapping("/change/{id}")
    public ResponseEntity<VetDTO> change(@RequestBody @Valid VetDTO vetDTO, @PathVariable Long id) {
        VetDTO updatedVet = vetService.updateVet(id, vetDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedVet);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}/appointments")
    public ResponseEntity<Void> deleteAllAppointmentsFromVet(@PathVariable Long id) {
        vetService.deleteAllAppointmentsFromVet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vetService.deleteVet(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}

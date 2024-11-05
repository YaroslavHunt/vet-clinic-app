package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.pet.AnyPetDTO;
import com.vetclinic.vetclinicapp.dto.pet.PetDTO;
import com.vetclinic.vetclinicapp.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<AnyPetDTO>> getAll(@RequestParam(required = false) Long ownerId) {
        List<AnyPetDTO> pets;
        pets = ownerId == null ? petService.findAll() : petService.findAllByOwnerId(ownerId);

        return pets.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long id) {
        PetDTO dto = petService.getPetById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/save")
    public ResponseEntity<AnyPetDTO> add(@RequestBody @Valid AnyPetDTO dto) {
        AnyPetDTO createdPet = petService.addPet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<AnyPetDTO> change(@RequestBody @Valid AnyPetDTO dto, @PathVariable Long id) {
        AnyPetDTO updatedPet = petService.updatePet(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedPet);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

}

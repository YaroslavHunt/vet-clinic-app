package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.PetDTO;
import com.vetclinic.vetclinicapp.exceptions.ApiError;
import com.vetclinic.vetclinicapp.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody @Valid PetDTO petDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage() != null
                            ? fieldError.getDefaultMessage()
                            : "Validation error in field: " + fieldError.getField())
                    .toList();

            ApiError apiError = new ApiError("Validation failed", errorMessages);
            return ResponseEntity.badRequest().body(apiError);
        }

        PetDTO savedPetDTO = petService.addPet(petDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPetDTO);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<PetDTO> change(@RequestBody PetDTO petDTO, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    }

}

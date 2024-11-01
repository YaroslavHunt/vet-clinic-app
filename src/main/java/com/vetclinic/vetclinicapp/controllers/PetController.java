package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.PetDTO;
import com.vetclinic.vetclinicapp.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void add(@RequestBody @Valid PetDTO petDTO) {

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

package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.VetDTO;
import com.vetclinic.vetclinicapp.models.Vet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {

    @GetMapping
    public ResponseEntity<List<VetDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetDTO> getVet(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    @ResponseStatus(CREATED)
    public void add(@RequestBody @Valid Vet vet) {}

    @PatchMapping("/change/{id}")
    public ResponseEntity<VetDTO> change(@RequestBody Vet vet, @PathVariable Long id){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {}

}

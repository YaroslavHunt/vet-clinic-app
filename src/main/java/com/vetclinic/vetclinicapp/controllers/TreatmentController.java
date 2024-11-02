package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.models.Treatment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    @GetMapping
    public ResponseEntity<List<TreatmentDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDTO> getTreatment(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    @ResponseStatus(CREATED)
    public void add(@RequestBody @Valid Treatment treatment) {}

    @PatchMapping("/change/{id}")
    public ResponseEntity<TreatmentDTO> change(@RequestBody Treatment treatment, @PathVariable Long id){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {}

}

package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.OwnerDTO;
import com.vetclinic.vetclinicapp.models.Owner;
import com.vetclinic.vetclinicapp.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Owner owner) {}

    @PatchMapping("/change/{id}")
    public ResponseEntity<OwnerDTO> change(@RequestBody Owner owner, @PathVariable Long id){
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {}

}
package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.dto.owner.OwnerDTO;
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
        return ownerService.findAll().isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long id) {
        OwnerDTO owner = ownerService.findById(id);
        return ResponseEntity.ok(owner);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> add(@RequestBody @Valid OwnerDTO dto) {
        ownerService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<OwnerDTO> change(@RequestBody OwnerDTO dto, @PathVariable Long id) {
        OwnerDTO owner = ownerService.update(dto, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(owner);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
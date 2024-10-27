package com.vetclinic.vetclinicapp.controllers;

import com.vetclinic.vetclinicapp.models.Pet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @GetMapping
    public List<Pet> getAllPets() {
        return null;
    }

    @GetMapping("/{id}")
    public Pet getPet() {
        return null;
    }

    @PostMapping("/save")
    public void addPet(@RequestBody Pet pet) {}

    @PatchMapping("/change/{id}")
    public void changePet(@RequestBody Pet pet) {}

    @DeleteMapping("/delete/{id}")
    public void deletePet(@PathVariable Long id) {}

}

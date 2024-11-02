package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.dto.pet.PetDTO;
import com.vetclinic.vetclinicapp.mappers.PetMapper;
import com.vetclinic.vetclinicapp.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public List<PetDTO> getAllPets() {
        return petRepository.findAll()
                .stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }

//    public PetDTO getPetById(Long id) {
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(() -> new GlobalExceptionHandler
//                        .ResourceNotFoundException("Pet not found with id: " + id));
//        return petMapper.toDTO(pet);
//    }

//    public PetDTO addPet(PetDTO petDTO) {
//        Pet pet = petMapper.toEntity(petDTO);
//        Pet savedPet = petRepository.save(pet);
//        return petMapper.toDTO(savedPet);
//    }

//    public PetDTO updatePet(Long id, Pet petDetails) {
//        Pet existingPet = petRepository.findById(id);
//
//        existingPet.setName(petDetails.getName());
//        existingPet.setType(petDetails.getType());
//        existingPet.setBreed(petDetails.getBreed());
//        existingPet.setAge(petDetails.getAge());
//        if (petDetails.getOwner() != null) {
//            existingPet.setOwner(petDetails.getOwner());
//        }
//
//        Pet updatedPet = petRepository.save(existingPet);
//        return petMapper.toDTO(updatedPet);
//    }
//
//    public void deletePet(Long id) {
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(() -> new GlobalExceptionHandler
//                        .ResourceNotFoundException("Pet not found with id: " + id));
//        petRepository.delete(pet);
//    }

}

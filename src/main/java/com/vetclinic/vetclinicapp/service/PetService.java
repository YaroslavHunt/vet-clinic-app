package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.pet.AnyPetDTO;
import com.vetclinic.vetclinicapp.dto.pet.PetDTO;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.PetMapper;
import com.vetclinic.vetclinicapp.entity.Owner;
import com.vetclinic.vetclinicapp.entity.Pet;
import com.vetclinic.vetclinicapp.repository.OwnerRepository;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final OwnerRepository ownerRepository;

    public List<AnyPetDTO> findAll() {
        return petRepository.findAll()
                .stream()
                .map(petMapper::toAnyDTO)
                .collect(Collectors.toList());
    }

    public List<AnyPetDTO> findAllByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(petMapper::toAnyDTO)
                .collect(Collectors.toList());
    }

    public PetDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Pet with id %d not found", id),
                                HttpStatus.NOT_FOUND));
        return petMapper.toDTO(pet);
    }

    public AnyPetDTO addPet(AnyPetDTO dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId()).orElseThrow(() ->
                new GlobalExceptionHandler.CustomException(
                        String.format("Owner with id %d not found", dto.getOwnerId()),
                        HttpStatus.NOT_FOUND)
        );

        Pet pet = petMapper.toAnyEntity(dto);
        pet.setOwner(owner);
        Pet savedPet = petRepository.save(pet);
        return petMapper.toAnyDTO(savedPet);
    }

    public AnyPetDTO updatePet(Long id, AnyPetDTO petData) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler
                .CustomException(
                String.format("Pet with id %d not found", id),
                HttpStatus.NOT_FOUND));

        Optional.ofNullable(petData.getName()).ifPresent(existingPet::setName);
        Optional.ofNullable(petData.getType()).ifPresent(existingPet::setType);
        Optional.ofNullable(petData.getBreed()).ifPresent(existingPet::setBreed);
        Optional.ofNullable(petData.getAge()).ifPresent(existingPet::setAge);
        Optional.ofNullable(petData.getOwnerId())
                .map(ownerId -> ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new GlobalExceptionHandler
                                .CustomException(
                                String.format("Owner with id %d not found", ownerId),
                                HttpStatus.NOT_FOUND)))
                .ifPresent(existingPet::setOwner);

        petRepository.save(existingPet);
        return petMapper.toAnyDTO(existingPet);
    }

    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler
                        .CustomException(
                        String.format("Pet with id %d not found", id),
                        HttpStatus.NOT_FOUND));
        petRepository.delete(pet);
    }

}

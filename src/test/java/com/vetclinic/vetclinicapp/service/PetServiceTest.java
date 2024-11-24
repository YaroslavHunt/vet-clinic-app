package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.pet.AnyPetDTO;
import com.vetclinic.vetclinicapp.entity.Owner;
import com.vetclinic.vetclinicapp.entity.Pet;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.PetMapper;
import com.vetclinic.vetclinicapp.repository.OwnerRepository;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Spy
    private final PetMapper petMapper = Mappers.getMapper(PetMapper.class);

    private PetService petService;

    @BeforeEach
    void setUp() {
        petService = new PetService(
                petRepository,
                petMapper,
                ownerRepository);
    }

    @Test
    void findAll_shouldReturnPetList() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<AnyPetDTO> pets = petService.findAll();

        assertFalse(pets.isEmpty());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void findAllByOwnerId_shouldReturnPetList() {
        Long ownerId = 1L;
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petRepository.findAllByOwnerId(ownerId)).thenReturn(List.of(pet));

        List<AnyPetDTO> pets = petService.findAllByOwnerId(ownerId);

        assertFalse(pets.isEmpty());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void getPetById_shouldThrowExceptionIfNotFound() {
        Long petId = 1L;

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> petService.getPetById(petId));
    }

    @Test
    void addPet_shouldThrowExceptionIfOwnerNotFound() {
        AnyPetDTO petDTO = new AnyPetDTO();
        petDTO.setOwnerId(1L);

        when(ownerRepository.findById(petDTO.getOwnerId())).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> petService.addPet(petDTO));
    }

    @Test
    void addPet_shouldSavePet() {
        AnyPetDTO petDTO = new AnyPetDTO();
        petDTO.setName("Buddy");
        petDTO.setOwnerId(1L);

        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerRepository.findById(petDTO.getOwnerId())).thenReturn(Optional.of(owner));

        Pet pet = petMapper.toAnyEntity(petDTO);
        pet.setOwner(owner);
        when(petRepository.save(pet)).thenReturn(pet);

        AnyPetDTO savedPet = petService.addPet(petDTO);

        assertNotNull(savedPet);
        assertEquals("Buddy", savedPet.getName());
        assertEquals(1L, savedPet.getOwnerId());
    }

    @Test
    void updatePet_shouldThrowExceptionIfPetNotFound() {
        Long petId = 1L;
        AnyPetDTO petDTO = new AnyPetDTO();
        petDTO.setName("Updated Name");

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> petService.updatePet(petId, petDTO));
    }

    @Test
    void updatePet_shouldUpdatePetDetails() {
        Long petId = 1L;
        AnyPetDTO petDTO = new AnyPetDTO();
        petDTO.setName("Updated Name");

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Old Name");

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(petRepository.save(pet)).thenReturn(pet);

        AnyPetDTO updatedPet = petService.updatePet(petId, petDTO);

        assertEquals("Updated Name", updatedPet.getName());
    }

    @Test
    void deletePet_shouldThrowExceptionIfPetNotFound() {
        Long petId = 1L;

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> petService.deletePet(petId));
    }

    @Test
    void deletePet_shouldDeletePet() {
        Long petId = 1L;
        Pet pet = new Pet();
        pet.setId(petId);

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).delete(pet);

        petService.deletePet(petId);

        verify(petRepository, times(1)).delete(pet);
    }
}

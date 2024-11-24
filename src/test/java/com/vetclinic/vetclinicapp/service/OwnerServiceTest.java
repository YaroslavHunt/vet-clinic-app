package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.owner.OwnerDTO;
import com.vetclinic.vetclinicapp.entity.Owner;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.OwnerMapper;
import com.vetclinic.vetclinicapp.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Spy
    private final OwnerMapper ownerMapper = Mappers.getMapper(OwnerMapper.class);

    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        ownerService = new OwnerService(ownerRepository, ownerMapper);
    }

    @Test
    void findById_shouldThrowExceptionIfNotFound() {
        Long ownerId = 1L;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.findById(ownerId));
    }

    @Test
    void save_shouldThrowExceptionIfEmailAlreadyExists() {
        OwnerDTO dto = new OwnerDTO();
        dto.setEmail("test@example.com");

        when(ownerRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.save(dto));
    }

    @Test
    void save_shouldThrowExceptionIfPhoneAlreadyExists() {
        OwnerDTO dto = new OwnerDTO();
        dto.setPhone("123456789");

        when(ownerRepository.existsByPhone(dto.getPhone())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.save(dto));
    }

    @Test
    void update_shouldThrowExceptionIfOwnerNotFound() {
        Long ownerId = 999L;
        OwnerDTO dto = new OwnerDTO();
        dto.setFirstName("New Name");

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.update(dto, ownerId));
    }

    @Test
    void update_shouldUpdateOwnerDetails() {
        Long ownerId = 1L;
        OwnerDTO dto = new OwnerDTO();
        dto.setFirstName("Updated Name");

        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setFirstName("Old Name");

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(owner)).thenReturn(owner);

        OwnerDTO updatedOwner = ownerService.update(dto, ownerId);

        assertEquals("Updated Name", updatedOwner.getFirstName());
    }

    @Test
    void deleteOwner_shouldThrowExceptionIfOwnerNotFound() {
        Long ownerId = 999L;

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.deleteOwner(ownerId));
    }

    @Test
    void deleteOwner_shouldDeleteOwner() {
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        doNothing().when(ownerRepository).deleteById(ownerId);

        ownerService.deleteOwner(ownerId);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class,
                () -> ownerService.findById(ownerId));
    }
}

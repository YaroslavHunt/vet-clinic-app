package com.vetclinic.vetclinicapp.service;

import com.vetclinic.vetclinicapp.dto.owner.OwnerDTO;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.mapper.OwnerMapper;
import com.vetclinic.vetclinicapp.models.Owner;
import com.vetclinic.vetclinicapp.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public List<OwnerDTO> findAll() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OwnerDTO findById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() ->
                        new GlobalExceptionHandler
                                .CustomException(
                                String.format("Owner with id %d not found", id),
                                HttpStatus.NOT_FOUND));
        return ownerMapper.toDTO(owner);
    }

    public void save(OwnerDTO dto) {
        if (ownerRepository.existsByEmail(dto.getEmail())) {
            throw new GlobalExceptionHandler.CustomException(
                    String.format("Owner with email %s already exists", dto.getEmail()),
                    HttpStatus.BAD_REQUEST);
        }

        if (ownerRepository.existsByPhone(dto.getPhone())) {
            throw new GlobalExceptionHandler.CustomException(
                    String.format("Owner with phone number %s already exists", dto.getPhone()),
                    HttpStatus.BAD_REQUEST);
        }

        Owner owner = ownerMapper.toEntity(dto);
        ownerRepository.save(owner);
    }

    public OwnerDTO update(OwnerDTO dto, Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() ->
                new GlobalExceptionHandler
                        .CustomException(
                        String.format("Owner with id %d not found", id),
                        HttpStatus.NOT_FOUND));

        Optional.ofNullable(dto.getFirstName()).ifPresent(owner::setFirstName);
        Optional.ofNullable(dto.getLastName()).ifPresent(owner::setLastName);
        Optional.ofNullable(dto.getEmail()).ifPresent(owner::setEmail);
        Optional.ofNullable(dto.getPhone()).ifPresent(owner::setPhone);

        ownerRepository.save(owner);
        return ownerMapper.toDTO(owner);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}

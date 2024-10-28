package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.PetDTO;
import com.vetclinic.vetclinicapp.models.Pet;
import org.mapstruct.Mapper;

@Mapper
public interface PetMapper {
    Pet toEntity(PetDTO dto);
    PetDTO toDTO(Pet pet);
}

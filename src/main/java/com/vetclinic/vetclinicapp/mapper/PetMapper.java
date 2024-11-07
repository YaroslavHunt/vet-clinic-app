package com.vetclinic.vetclinicapp.mapper;

import com.vetclinic.vetclinicapp.dto.pet.AnyPetDTO;
import com.vetclinic.vetclinicapp.dto.pet.PetDTO;
import com.vetclinic.vetclinicapp.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PetMapper {
    Pet toEntity(PetDTO dto);
    Pet toAnyEntity(AnyPetDTO dto);
    @Mapping(target = "ownerId", source = "owner.id")
    PetDTO toDTO(Pet pet);
    @Mapping(target = "ownerId", source = "owner.id")
    AnyPetDTO toAnyDTO(Pet pet);
}

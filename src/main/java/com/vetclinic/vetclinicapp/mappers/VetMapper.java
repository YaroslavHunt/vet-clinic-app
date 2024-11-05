package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.vet.VetDTO;
import com.vetclinic.vetclinicapp.models.Vet;
import org.mapstruct.Mapper;

@Mapper
public interface VetMapper {
    Vet toEntity(VetDTO dto);
    VetDTO toDTO(Vet vet);
}

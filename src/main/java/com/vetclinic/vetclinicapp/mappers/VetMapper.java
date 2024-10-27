package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.VetDTO;
import com.vetclinic.vetclinicapp.models.Vet;
import org.mapstruct.Mapper;

@Mapper
public interface VetMapper {
    Vet toDTO(VetDTO dto);
    VetDTO toEntity(Vet vet);
}

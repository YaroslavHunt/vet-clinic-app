package com.vetclinic.vetclinicapp.mapper;

import com.vetclinic.vetclinicapp.dto.vet.VetDTO;
import com.vetclinic.vetclinicapp.entity.Vet;
import org.mapstruct.Mapper;

@Mapper
public interface VetMapper {
    Vet toEntity(VetDTO dto);
    VetDTO toDTO(Vet vet);
}

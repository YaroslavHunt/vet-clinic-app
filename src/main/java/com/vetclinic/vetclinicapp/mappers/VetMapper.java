package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.VetDTO;
import com.vetclinic.vetclinicapp.models.Vet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VetMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "specialization", target = "specialization")
    @Mapping(source = "experience", target = "experience")
    Vet toEntity(VetDTO dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "specialization", target = "specialization")
    @Mapping(source = "experience", target = "experience")
    VetDTO toDTO(Vet vet);
}

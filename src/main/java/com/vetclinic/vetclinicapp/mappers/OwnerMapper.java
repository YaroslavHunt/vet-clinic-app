package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.OwnerDTO;
import com.vetclinic.vetclinicapp.models.Owner;
import org.mapstruct.Mapper;

@Mapper
public interface OwnerMapper {
    Owner toEntity(OwnerDTO dto);
    OwnerDTO toDTO(Owner owner);
}

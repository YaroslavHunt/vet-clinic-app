package com.vetclinic.vetclinicapp.mapper;

import com.vetclinic.vetclinicapp.dto.owner.OwnerDTO;
import com.vetclinic.vetclinicapp.models.Owner;
import org.mapstruct.Mapper;

@Mapper
public interface OwnerMapper {
    Owner toEntity(OwnerDTO dto);
    OwnerDTO toDTO(Owner owner);
}

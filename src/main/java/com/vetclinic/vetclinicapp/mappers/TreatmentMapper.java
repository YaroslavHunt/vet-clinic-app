package com.vetclinic.vetclinicapp.mappers;

import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.models.Treatment;
import org.mapstruct.Mapper;

@Mapper
public interface TreatmentMapper {
    Treatment toEntity(TreatmentDTO dto);
    TreatmentDTO toDTO(Treatment treatment);
}

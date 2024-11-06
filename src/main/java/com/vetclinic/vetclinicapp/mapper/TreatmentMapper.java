package com.vetclinic.vetclinicapp.mapper;

import com.vetclinic.vetclinicapp.dto.treatment.AnyTreatmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import com.vetclinic.vetclinicapp.models.Treatment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TreatmentMapper {
    Treatment toEntity(TreatmentDTO dto);
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "appointmentId", source = "appointment.id")
    TreatmentDTO toDTO(Treatment treatment);
    AnyTreatmentDTO toAnyDTO(Treatment treatment);
}

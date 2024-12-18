package com.vetclinic.vetclinicapp.dto.pet;

import com.vetclinic.vetclinicapp.dto.appointment.AppointmentDTO;
import com.vetclinic.vetclinicapp.dto.treatment.TreatmentDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private Long id;

    @NotBlank(message = "The name field is required")
    private String name;

    @NotBlank(message = "The type field is required")
    private String type;

    private String breed;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull
    private Long ownerId;

    private List<AppointmentDTO> appointments;

    private List<TreatmentDTO> treatments;

}

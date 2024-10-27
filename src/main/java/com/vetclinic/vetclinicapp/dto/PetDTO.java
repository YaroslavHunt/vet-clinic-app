package com.vetclinic.vetclinicapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;

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

    private OwnerDTO owner;

    private List<AppointmentDTO> appointments;

    private List<TreatmentDTO> treatments;

}

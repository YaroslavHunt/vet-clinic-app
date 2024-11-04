package com.vetclinic.vetclinicapp.dto.owner;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    private Long id;

    @NotBlank(message = "The name field is required")
    private String firstName;

    @NotBlank(message = "The lastname field is required")
    private String lastName;

    @NotBlank(message = "The email field is required")
    @UniqueElements(message = "Email must be unique")
    private String email;

//    private Set<@Valid PetDTO> pets;
}
package com.vetclinic.vetclinicapp.dto.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Email(message = "Invalid email format")
    @NotBlank(message = "The email field is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must follow the format: user@example.com"
    )
    @UniqueElements(message = "Email must be unique")
    private String email;

    @NotBlank(message = "The phone field is required")
    @Pattern(
            regexp = "^\\+?\\d{10,15}$\n",
            message = "Phone number must be in the format: +1234567890 or 1234567890"
    )
    private String phone;

}
package com.vetclinic.vetclinicapp.dto.owner;

import com.vetclinic.vetclinicapp.constants.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            regexp = Constants.EMAIL_REGEX,
            message = "Email must follow the format: user@example.com"
    )
    private String email;

    @NotBlank(message = "The phone field is required")
    @Pattern(
            regexp = Constants.PHONE_NUMBER_REGEX,
            message = "Invalid phone number format. Valid formats are: " + Constants.PHONE_NUMBER_FORMATS
    )
    private String phone;

}
package com.vetclinic.vetclinicapp.dto;

import com.vetclinic.vetclinicapp.models.Appointment;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public class VetDTO {
    private Long id;

    @NotBlank(message = "The name field is required")
    private String name;

    @NotBlank(message = "The email field is required")
    @UniqueElements(message = "Email must be unique")
    private String email;

    @NotBlank(message = "The phone field is required")
    @UniqueElements(message = "Phone number must be unique")
    private String phone;

    @NotBlank(message = "The specialization field is required")
    private String specialization;

    @NotBlank(message = "The experience field is required")
    private Integer experience;

    private List<Appointment> appointments;
}

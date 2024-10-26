package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}

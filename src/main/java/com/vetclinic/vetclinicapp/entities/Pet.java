package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String breed;

    private Integer age;

    @ManyToOne
    private Owner owner;
}

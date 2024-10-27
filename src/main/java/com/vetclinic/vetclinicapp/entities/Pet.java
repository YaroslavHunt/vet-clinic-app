package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private String breed;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne(optional = false)
    private Owner owner;
}

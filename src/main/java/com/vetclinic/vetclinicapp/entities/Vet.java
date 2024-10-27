package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String specialization;

    private Integer experience;
}

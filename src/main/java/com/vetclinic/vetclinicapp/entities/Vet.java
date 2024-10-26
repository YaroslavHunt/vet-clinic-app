package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String specialization;

    private Integer experience;
}

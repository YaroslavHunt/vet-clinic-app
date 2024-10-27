package com.vetclinic.vetclinicapp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String description;

    private Double cost;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime treatmentDate;
}


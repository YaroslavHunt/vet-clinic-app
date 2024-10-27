package com.vetclinic.vetclinicapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Treatment> treatments;
}

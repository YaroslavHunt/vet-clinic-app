package com.vetclinic.vetclinicapp.repositories;

import com.vetclinic.vetclinicapp.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
}

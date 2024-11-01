package com.vetclinic.vetclinicapp.repositories;

import com.vetclinic.vetclinicapp.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Vet> findAllByExperienceGreaterThan(Integer experience);
}

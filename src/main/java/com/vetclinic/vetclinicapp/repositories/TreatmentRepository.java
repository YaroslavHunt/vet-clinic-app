package com.vetclinic.vetclinicapp.repositories;

import com.vetclinic.vetclinicapp.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}

package com.vetclinic.vetclinicapp.repository;

import com.vetclinic.vetclinicapp.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByTreatmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Treatment> findByPetId(Long petId);

}

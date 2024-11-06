package com.vetclinic.vetclinicapp.repositories;

import com.vetclinic.vetclinicapp.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerId(Long ownerId);

    @Query("SELECT p FROM Pet p WHERE p.id IN (" +
            "SELECT a.pet.id FROM Appointment a WHERE a.appointmentDate = " +
            "(SELECT MAX(a2.appointmentDate) FROM Appointment a2 WHERE a2.pet.id = a.pet.id) " +
            "AND a.appointmentDate < :oneYearAgo)")
    List<Pet> findPetsWithLastAppointmentOlderThanOneYear(@Param("oneYearAgo") LocalDateTime oneYearAgo);
}

package com.vetclinic.vetclinicapp.repository;

import com.vetclinic.vetclinicapp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByVetId(Long vetId);

    List<Appointment> findAllByPetId(Long petId);

    List<Appointment> findAllByVetIdAndPetId(Long vetId, Long petId);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate >= :startOfDay AND a.appointmentDate < :endOfDay")
    Collection<Appointment> findAllByAppointmentDate(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}

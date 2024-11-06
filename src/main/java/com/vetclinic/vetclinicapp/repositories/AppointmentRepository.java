package com.vetclinic.vetclinicapp.repositories;

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

    Collection<Appointment> findAllByAppointmentDate(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    List<Appointment> findByPetIdAndAppointmentDateBefore(Long petId, LocalDateTime date);

}

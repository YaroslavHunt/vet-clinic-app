package com.vetclinic.vetclinicapp.repository;

import com.vetclinic.vetclinicapp.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}

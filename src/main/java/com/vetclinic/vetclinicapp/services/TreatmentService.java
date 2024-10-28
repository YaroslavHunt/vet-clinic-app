package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.mappers.TreatmentMapper;
import com.vetclinic.vetclinicapp.repositories.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
}

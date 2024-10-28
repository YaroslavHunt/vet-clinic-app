package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.mappers.AppointmentMapper;
import com.vetclinic.vetclinicapp.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

}

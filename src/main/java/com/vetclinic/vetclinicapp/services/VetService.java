package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.mappers.VetMapper;
import com.vetclinic.vetclinicapp.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;
}

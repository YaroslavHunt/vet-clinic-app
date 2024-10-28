package com.vetclinic.vetclinicapp.services;

import com.vetclinic.vetclinicapp.mappers.OwnerMapper;
import com.vetclinic.vetclinicapp.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
}

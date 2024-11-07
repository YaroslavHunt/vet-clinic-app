package com.vetclinic.vetclinicapp.service.mail;

import com.vetclinic.vetclinicapp.entity.Owner;
import com.vetclinic.vetclinicapp.entity.Pet;
import com.vetclinic.vetclinicapp.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final PetRepository petRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendYearlyReminderEmails() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        List<Pet> petsWithOldAppointments = petRepository.findPetsWithLastAppointmentOlderThanOneYear(oneYearAgo);

        for (Pet pet : petsWithOldAppointments) {
            Owner owner = pet.getOwner();
            emailService.sendReminderEmail(owner.getEmail(), pet.getName());
        }
    }
}

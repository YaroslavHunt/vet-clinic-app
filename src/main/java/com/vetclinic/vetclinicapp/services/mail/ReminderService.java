package com.vetclinic.vetclinicapp.services.mail;

import com.vetclinic.vetclinicapp.models.Appointment;
import com.vetclinic.vetclinicapp.models.Owner;
import com.vetclinic.vetclinicapp.models.Pet;
import com.vetclinic.vetclinicapp.repositories.AppointmentRepository;
import com.vetclinic.vetclinicapp.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendYearlyReminderEmails() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);

        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {
            List<Appointment> oldAppointments = appointmentRepository
                    .findByPetIdAndAppointmentDateBefore(pet.getId(), oneYearAgo);

            if (!oldAppointments.isEmpty()) {
                Owner owner = pet.getOwner();
                emailService.sendReminderEmail(owner.getEmail(), pet.getName());
            }
        }
    }
}

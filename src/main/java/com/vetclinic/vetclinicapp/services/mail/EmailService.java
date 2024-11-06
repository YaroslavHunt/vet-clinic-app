package com.vetclinic.vetclinicapp.services.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendReminderEmail(String to, String petName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Нагадування про обстеження");

            String content = "<html>" +
                    "<body>" +
                    "<h2>Шановний власнику!</h2>" +
                    "<p>Минув рік від останнього обстеження вашого улюбленця, <strong>" + petName + "</strong>.</p>" +
                    "<p>Варто завітати до нас на обстеження для підтримки здоров'я вашого вихованця!</p>" +
                    "<br><p>З повагою,<br>Команда Vet Clinic</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);

            mailSender.send(message);

            log.info("Reminder email successfully sent to {}", to);

        } catch (MessagingException e) {
            log.error("Error while sending email to {}: {}", to, e.getMessage());
        }
    }
}
package com.booking.system.service.impl;

import com.booking.system.service.api.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender emailSender;
    private static final String NOREPLY_ADDRESS = "noreply@booking.com";

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment, String attachmentName) {
        log.info("sending ticket to user {} via email", to);

        Thread thread = new Thread(() -> {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(NOREPLY_ADDRESS);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text);

                FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment(attachmentName, file);

                emailSender.send(message);
            } catch (MessagingException e) {
                log.error(e.toString());
            }
        });
        thread.start();

        log.info("email was successfully sent");
    }
}

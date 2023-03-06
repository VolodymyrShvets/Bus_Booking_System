package com.booking.system.service.impl;

import com.booking.system.model.dto.TicketDTO;
import com.booking.system.service.api.EmailService;

import com.booking.system.service.api.TicketService;
import com.booking.system.utility.PdfUtil;
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
    private TicketService ticketService;
    private static final String NOREPLY_ADDRESS = "noreply@booking.com";
    private static String emailSubject = "Bus Ticket";
    private static String emailText = "Your bus ticket. Thanks for using our system.";

    @Override
    public void sendMessageWithAttachment(String to, String id) {
        log.info("sending ticket to user {} via email", to);
        TicketDTO ticketDTO = ticketService.getTicketById(id);
        PdfUtil.createPdf(ticketDTO);

        String attachmentName = PdfUtil.getFileSimpleName(
                ticketDTO.getUserFirstName(),
                ticketDTO.getUserLastName())
                + PdfUtil.extension;

        String pathToAttachment = PdfUtil.getTicketName(
                ticketDTO.getUserFirstName(),
                ticketDTO.getUserLastName());

        Thread thread = new Thread(() -> {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(NOREPLY_ADDRESS);
                helper.setTo(to);
                helper.setSubject(emailSubject);
                helper.setText(emailText);

                FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment(attachmentName, file);

                emailSender.send(message);

                String[] arr = attachmentName.split("_");
                PdfUtil.deleteFile(arr[0], arr[1]);
            } catch (MessagingException e) {
                log.error(e.toString());
            }
        });
        thread.start();

        log.info("email was successfully sent");
    }
}

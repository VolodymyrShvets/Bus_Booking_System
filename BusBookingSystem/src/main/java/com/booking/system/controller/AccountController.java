package com.booking.system.controller;

import com.booking.system.model.dto.TicketDTO;
import com.booking.system.service.api.EmailService;
import com.booking.system.service.api.TicketService;
import com.booking.system.utility.PdfUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class AccountController {
    private TicketService ticketService;
    private EmailService emailService;

    @GetMapping(value = "/account/tickets")
    public String getUserTickets(Model model) {
        String email = getAuthentication().getName();
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }

    @GetMapping(value = "/account/ticket/{id}")
    public String sendTicketToEmail(@PathVariable String id) {
        String email = getAuthentication().getName();
        String subject = "Bus Ticket";
        String text = "Your bus ticket. Thanks for using our system.";

        TicketDTO ticketDTO = ticketService.getTicketById(id);
        PdfUtil.createPdf(ticketDTO);

        emailService.sendMessageWithAttachment(
                email,
                subject,
                text,
                PdfUtil.getTicketName(
                        ticketDTO.getUserFirstName(),
                        ticketDTO.getUserLastName()),
                PdfUtil.getFileSimpleName(
                        ticketDTO.getUserFirstName(),
                        ticketDTO.getUserLastName()) + PdfUtil.extension);
        return "account";
    }

    @GetMapping(value = "/account/tickets/delete/{busName}/{seat}")
    public String deleteTicket(@PathVariable String busName, @PathVariable String seat, Model model) {
        String email = getAuthentication().getName();
        ticketService.deleteTicket(busName, Long.parseLong(seat));
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

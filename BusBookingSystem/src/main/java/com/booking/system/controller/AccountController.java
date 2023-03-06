package com.booking.system.controller;

import com.booking.system.security.authentication.IAuthenticationFacade;
import com.booking.system.service.api.EmailService;
import com.booking.system.service.api.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class AccountController {
    private IAuthenticationFacade authenticationFacade;
    private TicketService ticketService;
    private EmailService emailService;

    @GetMapping(value = "/account/tickets")
    public String getUserTickets(Model model) {
        String email = authenticationFacade.getAuthentication().getName();
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }

    @GetMapping(value = "/account/ticket/{id}")
    public String sendTicketToEmail(@PathVariable String id) {
        String email = authenticationFacade.getAuthentication().getName();
        emailService.sendMessageWithAttachment(email, id);
        return "redirect:/account/tickets?success=true";
    }

    @GetMapping(value = "/account/tickets/delete/{busName}/{seat}")
    public String deleteTicket(@PathVariable String busName, @PathVariable String seat, Model model) {
        String email = authenticationFacade.getAuthentication().getName();
        ticketService.deleteTicket(busName, Long.parseLong(seat));
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }
}

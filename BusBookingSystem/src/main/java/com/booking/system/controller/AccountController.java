package com.booking.system.controller;

import com.booking.system.security.authentication.IAuthenticationFacade;
import com.booking.system.service.api.EmailService;
import com.booking.system.service.api.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@CrossOrigin
@AllArgsConstructor
public class AccountController {
    private IAuthenticationFacade authenticationFacade;
    private TicketService ticketService;
    private EmailService emailService;

    @ResponseStatus(HttpStatus.OK)
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

    @GetMapping(value = "/account/ticket/return/{id}")
    public String returnTicket(@PathVariable String id, Model model) {
        String email = authenticationFacade.getAuthentication().getName();
        ticketService.returnTicket(id);
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "redirect:/account/tickets?return=true";
    }

    @GetMapping(value = "/account/tickets/delete/{busName}/{seat}")
    public String deleteTicket(@PathVariable String busName, @PathVariable String seat, Model model) {
        String email = authenticationFacade.getAuthentication().getName();
        ticketService.deleteTicket(busName, Long.parseLong(seat));
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "redirect:/account/tickets?delete=true";
    }
}

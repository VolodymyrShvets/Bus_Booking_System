package com.booking.system.controller;

import com.booking.system.service.api.TicketService;
import com.booking.system.utility.HexConvertor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {
    private HexConvertor convertor;
    private TicketService ticketService;

    @GetMapping(value = "/account")
    public String getUserTickets(Model model) {
        model.addAttribute("convertor", convertor);
        return "account";
    }

    @GetMapping(value = "/account/tickets/{email}")
    public String getUserTickets(@PathVariable String email, Model model) {
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }

    @PostMapping(value = "/ticket/email") // TODO create pdf to email mechanism
    public void sendTicketToEmail() {
        System.out.println("ticket sent");
    }

    @GetMapping(value = "/account/tickets/delete/{busName}/{seat}/{email}")
    public String deleteTicket(@PathVariable String busName, @PathVariable String seat, @PathVariable String email, Model model) {
        ticketService.deleteTicket(busName, Long.parseLong(seat));
        model.addAttribute("tickets", ticketService.getAllTicketsByUserEmail(email));
        return "account";
    }
}

package com.booking.system.controller;

import com.booking.system.model.TicketStatus;
import com.booking.system.model.dto.BusDTO;
import com.booking.system.model.dto.TicketDTO;
import com.booking.system.security.authentication.IAuthenticationFacade;
import com.booking.system.security.userdetails.CustomUserDetails;
import com.booking.system.service.api.BusService;
import com.booking.system.service.api.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {
    private BusService busService;
    private TicketService ticketService;
    private IAuthenticationFacade authenticationFacade;
    private TicketDTO ticketDTO;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/preorder/{name}")
    public String getBusByName(@PathVariable("name") String name, Model model) {
        BusDTO currentBus = busService.getBusByBusName(name);
        model.addAttribute("currentBus", List.of(currentBus));
        return "preorder";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/checkout/{name}")
    public String getBusByName2(@PathVariable("name") String name, Model model) {
        BusDTO currentBus = busService.getBusByBusName(name);
        model.addAttribute("currentBus", List.of(currentBus));
        model.addAttribute("ticket", ticketCreationForm());
        return "checkout";
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/checkout/new/{name}")
    public String addNewTicket(@PathVariable("name") String name, @ModelAttribute("ticket") TicketDTO ticketDTO) {
        BusDTO busDTO = busService.getBusByBusName(name);

        ticketDTO.setBusName(busDTO.getName());
        ticketDTO.setBusDepartureCity(busDTO.getDepartureCity());
        ticketDTO.setBusArrivalCity(busDTO.getArrivalCity());
        ticketDTO.setBusDepartureTime(busDTO.getDepartureTime());
        ticketDTO.setBusArrivalTime(busDTO.getArrivalTime());
        ticketDTO.setPrice(busDTO.getTicketPrice());

        ticketDTO.setStatus(TicketStatus.ACTIVE);

        this.ticketDTO = ticketDTO;

        return "payment";
    }

    @PostMapping("/payment")
    public String postTicket() {
        busService.updateBus(ticketDTO.getBusName(), ticketDTO.getSeat());
        ticketService.insertNewTicket(ticketDTO);
        ticketDTO = null;
        return "redirect:/?success=true";
    }

    public TicketDTO ticketCreationForm() {
        TicketDTO dto = new TicketDTO();
        Authentication auth = authenticationFacade.getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            dto.setUserFirstName(userDetails.getFirstName());
            dto.setUserLastName(userDetails.getLastName());
            dto.setUserEmail(userDetails.getEmail());
        }

        return dto;
    }
}

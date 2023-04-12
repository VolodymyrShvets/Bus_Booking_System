package com.booking.system.config;

import com.booking.system.model.dto.TicketDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public TicketDTO ticketDTO() {
        return new TicketDTO();
    }
}

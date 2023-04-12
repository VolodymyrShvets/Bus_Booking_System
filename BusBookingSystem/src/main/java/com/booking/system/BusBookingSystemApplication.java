package com.booking.system;

import com.booking.system.repository.BusRepository;
import com.booking.system.utility.Utility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BusBookingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusBookingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BusRepository repository) {
        return args -> {
            //Utility.saveBuses(repository, Utility.generateBuses());
            Utility.trimBusCollection(repository);
        };
    }
}
// TODO add feature for user to change his info (name, last name, email, password)
// TODO check if seat exists in bus, before book it
// TODO (previous upgrade) create transactions? for seat booking
// TODO add 'return ticket' function only if ticket is not EXPIRED
// TODO add alert icons and check colors of alerts

// TODO add to all controller advices @slf4j, log error and return /error instead of message

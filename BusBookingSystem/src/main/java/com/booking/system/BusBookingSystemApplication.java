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
// TODO (after previous) add deletion mechanism, if ticket is stored more than (1 month)
// TODO add email validator
// TODO add feature to user -> create PDF from ticket and than -> send ticket to email

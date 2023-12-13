package com.booking.system;

import com.booking.system.repository.BusRepository;
import com.booking.system.utility.Utility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class BusBookingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusBookingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BusRepository repository) {
        return args -> {
            Utility.trimBusCollection(repository);
            Utility.saveBuses(repository, Utility.generateBuses(30, LocalDate.now()));
        };
    }
}
// TODO add feature for user to change his info (name, last name, email, password)

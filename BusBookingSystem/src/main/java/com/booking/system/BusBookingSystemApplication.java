package com.booking.system;

import com.booking.system.repository.BusRepository;
import com.booking.system.utility.Utility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BusBookingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusBookingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BusRepository repository, Environment env) {
        return args -> {
            Utility.saveBusses(repository, env.getProperty("busses.json.location"));
            //Utility.saveBusses(repository, env.getProperty("busses-test.json.location")); // for tests
        };
    }
}

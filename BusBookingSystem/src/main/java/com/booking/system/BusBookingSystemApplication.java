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
        };
    }
}
// TODO create more buses with actual dates
// TODO (after previous) add busses for one month prior
// TODO (after previous) add search by dates
// TODO (after previous) add deletion mechanism, if bus' arrival date is < than date.now
// TODO (after previous) replace BusNotFoundException with message that no buses found
// TODO add feature for user to change his info (name, last name, email, password)
// TODO add feature for user to watch all his tickets
// TODO (after previous) update ticket' status to EXPIRED if ticket arrival date is < than date.now
// TODO (after previous) add deletion mechanism, is ticket is stored more than (1 month)
// TODO add message about successful ticket creation and same for user creation

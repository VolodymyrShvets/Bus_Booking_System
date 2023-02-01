package com.booking.system.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Component
@Document
public class Bus {
    @Id
    String id;
    @Indexed(unique = true)
    String name;
    String departureCity;
    String arrivalCity;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    List<Long> availableSeats;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

        return "Bus: [" +
                "\n\tid = " + id +
                ",\n\tname = '" + name + '\'' +
                ",\n\tsendingStation = '" + departureCity + '\'' +
                ",\n\tarrivalStation = '" + arrivalCity + '\'' +
                ",\n\tdateOfDeparture = " + departureTime.format(formatter) +
                ",\n\tdateOfArrival = " + arrivalTime.format(formatter) +
                ",\n\tavailableSeats: \n\t\t" + availableSeats +
                "\n]";
    }
}

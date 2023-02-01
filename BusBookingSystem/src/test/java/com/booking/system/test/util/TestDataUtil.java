package com.booking.system.test.util;

import com.booking.system.model.Bus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtil {
    public static final String TEST_BUS_NAME = "144D";
    public static final String TEST_DEPARTURE_CITY = "Zaporizhia";
    public static final String TEST_ARRIVAL_CITY = "Chernivci";
    private static final LocalDateTime TEST_DEPARTURE_TIME = LocalDateTime.of(2022, 2, 3, 18, 25);
    private static final LocalDateTime TEST_ARRIVAL_TIME = LocalDateTime.of(2022, 2, 4, 8, 45);
    private static final List<Long> TEST_AVAILABLE_SEATS = new ArrayList<>(Stream.iterate(1L, n -> n + 1).limit(20).toList());

    public static Bus createBus() {
        Bus bus = new Bus();

        bus.setName(TEST_BUS_NAME);
        bus.setDepartureCity(TEST_DEPARTURE_CITY);
        bus.setArrivalCity(TEST_ARRIVAL_CITY);
        bus.setAvailableSeats(TEST_AVAILABLE_SEATS);
        bus.setDepartureTime(TEST_DEPARTURE_TIME);
        bus.setArrivalTime(TEST_ARRIVAL_TIME);

        return bus;
    }
}

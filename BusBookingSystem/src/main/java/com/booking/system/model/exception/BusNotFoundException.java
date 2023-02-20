package com.booking.system.model.exception;

public class BusNotFoundException extends RuntimeException {
    public BusNotFoundException(String name) {
        super("Could not found Bus with Name: " + name);
    }

    public BusNotFoundException(String departure, String arrival) {
        super("Could not found Buses with Departure: " + departure + " and Arrival: " + arrival);
    }
}

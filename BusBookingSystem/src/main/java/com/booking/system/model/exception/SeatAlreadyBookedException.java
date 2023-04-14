package com.booking.system.model.exception;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(String busName, long seat) {
        super("Following seat: \'" + seat + "\' for Bus \'" + busName + "\' already been taken.");
    }
}

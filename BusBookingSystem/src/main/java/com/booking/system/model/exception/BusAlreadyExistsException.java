package com.booking.system.model.exception;

public class BusAlreadyExistsException extends RuntimeException {
    public BusAlreadyExistsException(String name) {
        super("Bus with this name " + name + " and parameters already exists in repository.");
    }
}

package com.booking.system.service.api;

import com.booking.system.model.Bus;

import java.util.List;

public interface BusService {
    Bus createNewBus(Bus bus);

    List<Bus> getBussesByArrivalCity(String arrivalCity);

    List<Bus> getBussesByDepartureCity(String departureCity);

    List<Bus> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);

    Bus updateBus(Bus bus);

    void deleteBus(String busName);
}

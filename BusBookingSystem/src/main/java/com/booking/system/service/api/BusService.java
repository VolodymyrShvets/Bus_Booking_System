package com.booking.system.service.api;

import com.booking.system.model.dto.BusDTO;

import java.util.List;

public interface BusService {
    BusDTO createNewBus(BusDTO busDTO);

    BusDTO getBusByBusName(String name);

    List<BusDTO> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity, String departureDate);

    BusDTO updateBus(BusDTO busDTO);

    BusDTO updateBus(String name, long seat);
}

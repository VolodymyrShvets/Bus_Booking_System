package com.booking.system.service.api;

import com.booking.system.model.Bus;
import com.booking.system.model.dto.BusDTO;

import java.util.List;

public interface BusService {
    BusDTO createNewBus(BusDTO busDTO);

    List<BusDTO> getBussesByArrivalCity(String arrivalCity);

    List<BusDTO> getBussesByDepartureCity(String departureCity);

    List<BusDTO> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);

    BusDTO updateBus(BusDTO busDTO);

    BusDTO updateBus(String name, long seat);

    void deleteBus(String busName);
}

package com.booking.system.service;

import com.booking.system.model.Bus;
import com.booking.system.repository.BusRepository;
import com.booking.system.service.api.BusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BusServiceImpl implements BusService {
    private BusRepository repository;

    @Override
    public Bus createNewBus(Bus bus) {
        log.info("Creating new Bus with name {}", bus.getName());
        return repository.save(bus);
    }

    @Override
    public List<Bus> getBussesByArrivalCity(String arrivalCity) {
        log.info("Receiving all Busses that arrive to {}", arrivalCity);
        return repository.findAllByArrivalCity(arrivalCity);
    }

    @Override
    public List<Bus> getBussesByDepartureCity(String departureCity) {
        log.info("Receiving all Busses that departs from {}", departureCity);
        return repository.findAllByDepartureCity(departureCity);
    }

    @Override
    public List<Bus> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
        log.info("Receiving all Busses that departs from {} and arrive to {}", departureCity, arrivalCity);
        return repository.findAllByDepartureCityAndArrivalCity(departureCity, arrivalCity);
    }

    @Override
    public Bus updateBus(Bus bus) {
        log.info("Updating the Bus with name {}", bus.getName());
        return repository.save(bus);
    }

    @Override
    public Bus updateBus(String name, long seat) {
        log.info("Updating the Bus with name {} -> booking seat {}", name, seat);
        Bus bus = repository.findByName(name);
        bus.getAvailableSeats().remove(seat);
        return repository.save(bus);
    }

    @Override
    public void deleteBus(String busName) {
        log.info("Deleting the Bus with name {}", busName);
        repository.deleteByName(busName);
    }
}

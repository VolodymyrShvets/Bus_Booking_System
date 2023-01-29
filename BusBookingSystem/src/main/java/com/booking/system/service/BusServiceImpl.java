package com.booking.system.service;

import com.booking.system.model.Bus;
import com.booking.system.repository.BusRepository;
import com.booking.system.service.api.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private BusRepository repository;

    @Override
    public Bus createNewBus(Bus bus) {
        return repository.save(bus);
    }

    @Override
    public List<Bus> getBussesByArrivalCity(String arrivalCity) {
        return repository.findAllByArrivalCity(arrivalCity);
    }

    @Override
    public List<Bus> getBussesByDepartureCity(String departureCity) {
        return repository.findAllByDepartureCity(departureCity);
    }

    @Override
    public List<Bus> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
        return repository.findAllByDepartureCityAndArrivalCity(departureCity, arrivalCity);
    }

    @Override
    public Bus updateBus(Bus bus) {
        return repository.save(bus);
    }

    @Override
    public void deleteBus(String busName) {
        repository.deleteByName(busName);
    }
}

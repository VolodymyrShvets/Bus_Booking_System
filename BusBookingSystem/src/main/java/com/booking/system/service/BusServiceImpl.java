package com.booking.system.service;

import com.booking.system.model.Bus;
import com.booking.system.model.exception.BusAlreadyExistsException;
import com.booking.system.model.exception.BusNotFoundException;
import com.booking.system.repository.BusRepository;
import com.booking.system.service.api.BusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BusServiceImpl implements BusService {
    private BusRepository repository;

    @Override
    public Bus createNewBus(Bus bus) {
        log.info("Creating new Bus with name {}", bus.getName());

        if (repository.existsByName(bus.getName()))
            throw new BusAlreadyExistsException(bus.getName());

        log.info("Bus with name {} successfully created", bus.getName());
        return repository.insert(bus);
    }

    @Override
    public List<Bus> getBussesByArrivalCity(String arrivalCity) {
        log.info("Receiving all Busses that arrive to {}", arrivalCity);
        List<Bus> buses = repository.findAllByArrivalCity(arrivalCity);
        if (buses.size() == 0)
            throw new BusNotFoundException(2, arrivalCity);
        return buses;
    }

    @Override
    public List<Bus> getBussesByDepartureCity(String departureCity) {
        log.info("Receiving all Busses that departs from {}", departureCity);
        List<Bus> buses = repository.findAllByDepartureCity(departureCity);
        if (buses.size() == 0)
            throw new BusNotFoundException(1, departureCity);
        return buses;
    }

    @Override
    public List<Bus> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
        log.info("Receiving all Busses that departs from {} and arrive to {}", departureCity, arrivalCity);
        List<Bus> buses = repository.findAllByDepartureCityAndArrivalCity(departureCity, arrivalCity);
        if (buses.size() == 0)
            throw new BusNotFoundException(departureCity, arrivalCity);
        return buses;
    }

    @Override
    public Bus updateBus(Bus bus) {
        log.info("Updating the Bus with name {}", bus.getName());
        Bus buss = repository.findByName(bus.getName());
        if (buss == null)
            throw new BusNotFoundException(bus.getName());

        Field[] fields = Bus.class.getDeclaredFields();
        Method[] methods = Arrays.stream(Bus.class.getMethods()).filter(method -> method.getName().startsWith("set")).toList().toArray(new Method[0]);

        for (int i = 1; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Method method = Arrays.stream(methods).filter(method1 -> method1.getName().toLowerCase().endsWith(field.getName().toLowerCase())).findFirst().get();
            try {
                var value = field.get(bus);
                if (value == null)
                    continue;
                method.invoke(buss, value);
            } catch (IllegalAccessException ex) {
                log.error("Illegal Argument Exception: Can't access field {}", field.getName());
            } catch (InvocationTargetException e) {
                log.error("Invocation Target Exception: Can't invoke method {}", method.getName());
                throw new RuntimeException(e);
            }
        }
        return repository.save(buss);
    }

    @Override
    public Bus updateBus(String name, long seat) {
        log.info("Updating the Bus with name {} -> booking seat {}", name, seat);
        Bus bus = repository.findByName(name);
        if (bus == null)
            throw new BusNotFoundException(name);
        bus.getAvailableSeats().remove(seat);
        return repository.save(bus);
    }

    @Override
    public void deleteBus(String busName) {
        log.info("Deleting the Bus with name {}", busName);
        repository.deleteByName(busName);
    }
}

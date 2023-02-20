package com.booking.system.service;

import com.booking.system.model.Bus;
import com.booking.system.model.dto.BusDTO;
import com.booking.system.model.exception.BusAlreadyExistsException;
import com.booking.system.model.exception.BusNotFoundException;
import com.booking.system.repository.BusRepository;
import com.booking.system.service.api.BusService;
import com.booking.system.service.mapper.BusMapper;
import com.booking.system.utility.Utility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BusServiceImpl implements BusService {
    private BusRepository repository;

    @Override
    public BusDTO createNewBus(BusDTO busDTO) {
        log.info("Creating new Bus with name {}", busDTO.getName());

        if (repository.existsByName(busDTO.getName()))
            throw new BusAlreadyExistsException(busDTO.getName());

        Bus bus = BusMapper.INSTANCE.busDTOToBus(busDTO);
        repository.insert(bus);

        log.info("Bus with name {} successfully created", busDTO.getName());
        return BusMapper.INSTANCE.busToBusDTO(bus);
    }

    @Override
    public BusDTO getBusByBusName(String name) {
        log.info("Receiving Bus with name {}", name);

        Bus bus = repository.findByName(name);
        return BusMapper.INSTANCE.busToBusDTO(bus);
    }

    @Override
    public List<BusDTO> getBussesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity, String date) {
        log.info("Receiving all Busses that departs from {} and arrive to {} on {}", departureCity, arrivalCity, date);
        LocalDate departureDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Bus> buses =
                repository.findAllByDepartureCityAndArrivalCity(
                        Utility.capitalize(departureCity),
                        Utility.capitalize(arrivalCity),
                        departureDate,
                        departureDate.plusDays(1));
        return buses
                .stream()
                .map(BusMapper.INSTANCE::busToBusDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BusDTO updateBus(BusDTO busDTO) {
        log.info("Updating the Bus with name {}", busDTO.getName());
        Bus persistedBus = repository.findByName(busDTO.getName());
        if (persistedBus == null)
            throw new BusNotFoundException(busDTO.getName());

        Field[] fields = Bus.class.getDeclaredFields();
        Method[] methods = Arrays.stream(Bus.class.getMethods()).filter(method -> method.getName().startsWith("set")).toList().toArray(new Method[0]);

        for (int i = 1; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Method method = Arrays.stream(methods).filter(method1 -> method1.getName().toLowerCase().endsWith(field.getName().toLowerCase())).findFirst().get();
            try {
                var value = field.get(busDTO);
                if (value == null)
                    continue;
                method.invoke(persistedBus, value);
            } catch (IllegalAccessException ex) {
                log.error("Illegal Argument Exception: Can't access field {}", field.getName());
            } catch (InvocationTargetException e) {
                log.error("Invocation Target Exception: Can't invoke method {}", method.getName());
                throw new RuntimeException(e);
            }
        }

        Bus storedBus = repository.save(persistedBus);
        log.info("Bus with name {} successfully updated", busDTO.getName());
        return BusMapper.INSTANCE.busToBusDTO(storedBus);
    }

    @Override
    public BusDTO updateBus(String name, long seat) {
        log.info("Updating the Bus with name {} -> booking seat {}", name, seat);
        Bus bus = repository.findByName(name);

        if (bus == null)
            throw new BusNotFoundException(name);
        bus.getAvailableSeats().remove(seat);

        Bus storedBus = repository.save(bus);
        log.info("Bus with name {} successfully updated", name);
        return BusMapper.INSTANCE.busToBusDTO(storedBus);
    }
}

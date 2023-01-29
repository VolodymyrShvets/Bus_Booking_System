package com.booking.system.controller;

import com.booking.system.model.Bus;
import com.booking.system.service.api.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/bus")
public class BusController {
    private BusService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Bus createNewBus(@RequestBody Bus bus) {
        return service.createNewBus(bus);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{departureCity}")
    public List<Bus> getBussesByDepartureCity(@PathVariable String departureCity) {
        return service.getBussesByDepartureCity(departureCity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{arrivalCity}")
    public List<Bus> getBussesByArrivalCity(@PathVariable String arrivalCity) {
        return service.getBussesByArrivalCity(arrivalCity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{departureCity}/{arrivalCity}")
    public List<Bus> findAllByDepartureCityAndArrivalCity(@PathVariable String departureCity, @PathVariable String arrivalCity) {
        return service.getBussesByDepartureCityAndArrivalCity(departureCity, arrivalCity);
    }

    /*          TODO update method -> pass seatNumber to delete it from seats array
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public Bus updateBus(@RequestBody Bus bus) {
        return service.updateBus(bus);
    }
     */

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Void> deleteBus(@PathVariable String name) {
        service.deleteBus(name);
        return ResponseEntity.noContent().build();
    }
}

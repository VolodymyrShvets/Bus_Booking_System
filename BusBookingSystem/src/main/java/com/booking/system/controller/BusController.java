package com.booking.system.controller;

import com.booking.system.model.SearchData;
import com.booking.system.model.dto.BusDTO;
import com.booking.system.service.api.BusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class BusController {
    private BusService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BusDTO createNewBus(@RequestBody BusDTO busDTO) {
        return service.createNewBus(busDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/bus/departure/{departureCity}")
    public String getBussesByDepartureCity(@PathVariable String departureCity, Model model) {
        model.addAttribute("buses", service.getBussesByDepartureCity(departureCity));
        return "index";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/bus/arrival/{arrivalCity}")
    public String getBussesByArrivalCity(@PathVariable String arrivalCity, Model model) {
        model.addAttribute("buses", service.getBussesByArrivalCity(arrivalCity));
        return "index";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/bus/search")
    public String findAllByDepartureCityAndArrivalCity(@ModelAttribute("searchData") SearchData searchData, Model model) {
        model.addAttribute("buses", service.getBussesByDepartureCityAndArrivalCity(searchData.getDepartureCity(), searchData.getArrivalCity()));
        return "index";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/bus")
    public BusDTO updateBus(@RequestBody BusDTO busDTO) {
        return service.updateBus(busDTO);
    }

    @DeleteMapping(value = "/bus/{name}")
    public ResponseEntity<Void> deleteBus(@PathVariable String name) {
        service.deleteBus(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/")
    public String showSearchForm(Model model) {
        model.addAttribute("searchData", searchData());
        return "index";
    }

    public SearchData searchData() {
        return new SearchData();
    }
}

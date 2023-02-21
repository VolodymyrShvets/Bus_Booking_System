package com.booking.system.controller;

import com.booking.system.model.SearchData;
import com.booking.system.model.dto.BusDTO;
import com.booking.system.service.api.BusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(value = "/bus/search")
    public String findAllByDepartureCityAndArrivalCity(@ModelAttribute("searchData") SearchData searchData, Model model) {
        List<BusDTO> list = service.getBussesByDepartureCityAndArrivalCity(searchData.getDepartureCity(), searchData.getArrivalCity(), searchData.getDepartureDate());
        if (list.size() == 0)
            model.addAttribute("noBuses", List.of(new BusDTO()));
        else
            model.addAttribute("buses", list);
        return "index";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/bus")
    public BusDTO updateBus(@RequestBody BusDTO busDTO) {
        return service.updateBus(busDTO);
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

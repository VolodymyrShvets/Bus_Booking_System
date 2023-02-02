package com.booking.system.controller;

import com.booking.system.model.Bus;
import com.booking.system.service.api.BusService;
import com.booking.system.test.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.booking.system.test.util.TestDataUtil.TEST_ARRIVAL_CITY;
import static com.booking.system.test.util.TestDataUtil.TEST_DEPARTURE_CITY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BusControllerTest {
    @Mock
    private BusService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBusByDepartureCityTest() throws Exception {
        Bus bus = TestDataUtil.createBus();

        when(service.getBussesByDepartureCity(TEST_DEPARTURE_CITY)).thenReturn(List.of(bus));

        mockMvc.perform(get("/bus/departure/" + TEST_DEPARTURE_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBusByArrivalCityTest() throws Exception {
        Bus bus = TestDataUtil.createBus();

        when(service.getBussesByArrivalCity(TEST_ARRIVAL_CITY)).thenReturn(List.of(bus));

        mockMvc.perform(get("/bus/arrival/" + TEST_ARRIVAL_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBusByDepartureAndArrivalCitiesTest() throws Exception {
        Bus bus = TestDataUtil.createBus();

        when(service.getBussesByDepartureCityAndArrivalCity(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY)).thenReturn(List.of(bus));

        mockMvc.perform(get("/bus/departure/" + TEST_DEPARTURE_CITY + "/arrival/" + TEST_ARRIVAL_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

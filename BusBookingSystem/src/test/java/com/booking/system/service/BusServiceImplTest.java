package com.booking.system.service;

import com.booking.system.model.Bus;
import com.booking.system.model.exception.BusAlreadyExistsException;
import com.booking.system.model.exception.BusNotFoundException;
import com.booking.system.repository.BusRepository;
import com.booking.system.test.util.TestDataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.booking.system.test.util.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class BusServiceImplTest {
    @Mock
    private BusRepository repository;
    @InjectMocks
    private BusServiceImpl service;

    @Test
    public void getBusByDepartureCityTest() {
        List<Bus> expected = List.of(TestDataUtil.createBus());
        when(repository.findAllByDepartureCity(TEST_DEPARTURE_CITY)).thenReturn(expected);

        List<Bus> testBusses = service.getBussesByDepartureCity(TEST_DEPARTURE_CITY);

        Assert.assertTrue(Arrays.deepEquals(expected.toArray(), testBusses.toArray()));
    }

    @Test
    public void getBusByArrivalCityTest() {
        List<Bus> expected = List.of(TestDataUtil.createBus());
        when(repository.findAllByArrivalCity(TEST_ARRIVAL_CITY)).thenReturn(expected);

        List<Bus> actual = service.getBussesByArrivalCity(TEST_ARRIVAL_CITY);

        Assert.assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    public void getBusByDepartureAndArrivalCityTest() {
        List<Bus> expected = List.of(TestDataUtil.createBus());
        when(repository.findAllByDepartureCityAndArrivalCity(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY)).thenReturn(expected);

        List<Bus> actual = service.getBussesByDepartureCityAndArrivalCity(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY);

        Assert.assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    public void busNotFoundTest1() {
        doThrow(new BusNotFoundException(TEST_ARRIVAL_CITY)).when(repository).findAllByArrivalCity(TEST_ARRIVAL_CITY);

        assertThrows(BusNotFoundException.class, () -> service.getBussesByArrivalCity(TEST_ARRIVAL_CITY));
    }

    @Test
    public void busNotFoundTest2() {
        doThrow(new BusNotFoundException(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY)).when(repository).findAllByDepartureCityAndArrivalCity(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY);

        assertThrows(BusNotFoundException.class, () -> service.getBussesByDepartureCityAndArrivalCity(TEST_DEPARTURE_CITY, TEST_ARRIVAL_CITY));
    }

    @Test
    public void createBusTest() {
        Bus expected = TestDataUtil.createBus();
        when(repository.insert(expected)).thenReturn(expected);

        Bus actual = service.createNewBus(expected);

        assertThat(actual, allOf(
                hasProperty("name", equalTo(expected.getName())),
                hasProperty("departureCity", equalTo(expected.getDepartureCity())),
                hasProperty("arrivalCity", equalTo(expected.getArrivalCity()))
        ));
    }

    @Test
    public void busAlreadyExistsTest() {
        Bus expected = TestDataUtil.createBus();
        when(repository.existsByName(TEST_BUS_NAME)).thenReturn(true);

        assertThrows(BusAlreadyExistsException.class, () -> service.createNewBus(expected));
    }

    @Test
    public void updateBusTest1() {
        Bus expected = TestDataUtil.createBus();
        when(repository.findByName(TEST_BUS_NAME)).thenReturn(expected);
        when(repository.save(expected)).thenReturn(expected);

        Bus actual = service.updateBus(expected);

        assertThat(actual, allOf(
                hasProperty("name", equalTo(expected.getName())),
                hasProperty("departureCity", equalTo(expected.getDepartureCity())),
                hasProperty("arrivalCity", equalTo(expected.getArrivalCity()))
        ));
    }

    @Test
    public void updateBusTest2() {
        Bus expected = TestDataUtil.createBus();
        when(repository.findByName(TEST_BUS_NAME)).thenReturn(expected);
        when(repository.save(expected)).thenReturn(expected);

        Bus actual = service.updateBus(expected.getName(), 20L);

        assertThat(actual, allOf(
                hasProperty("name", equalTo(expected.getName())),
                hasProperty("departureCity", equalTo(expected.getDepartureCity())),
                hasProperty("arrivalCity", equalTo(expected.getArrivalCity()))
        ));

        Assert.assertTrue(Arrays.deepEquals(actual.getAvailableSeats().toArray(), expected.getAvailableSeats().toArray()));
    }

    @Test
    public void updateBusNotFoundTest() {
        Bus expected = TestDataUtil.createBus();
        doThrow(new BusNotFoundException(TEST_BUS_NAME)).when(repository).findByName(TEST_BUS_NAME);

        assertThrows(BusNotFoundException.class, () -> service.updateBus(expected));
    }

    @Test
    public void deleteBusTest() {
        Bus expected = TestDataUtil.createBus();
        when(repository.findByName(TEST_BUS_NAME)).thenReturn(expected);

        service.deleteBus(TEST_BUS_NAME);

        verify(repository, times(1)).deleteByName(expected.getName());
    }

    @Test
    public void deleteBusNotFoundTest() {
        doThrow(new BusNotFoundException(TEST_BUS_NAME)).when(repository).findByName(TEST_BUS_NAME);

        assertThatCode(() -> service.deleteBus(TEST_BUS_NAME)).doesNotThrowAnyException();
    }
}

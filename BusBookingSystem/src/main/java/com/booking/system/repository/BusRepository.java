package com.booking.system.repository;

import com.booking.system.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {
    Bus findByName(String name);

    List<Bus> findAllByArrivalCity(String arrivalCity);

    List<Bus> findAllByDepartureCity(String departureCity);

    List<Bus> findAllByDepartureCityAndArrivalCity(@Param("departureCity") String departureCity, @Param("arrivalCity") String arrivalCity);

    void deleteByName(String busName);
}

package com.booking.system.repository;

import com.booking.system.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {
    Bus findByName(String name);

    boolean existsByName(String name);

    @Query("{ 'departureCity': '?0', 'arrivalCity': '?1', 'departureTime': { '$gte': ?2, '$lte': ?3 } }")
    List<Bus> findAllByDepartureCityAndArrivalCity(String departureCity, String arrivalCity, LocalDate startDate, LocalDate endDate);

    void deleteAllByDepartureTimeBefore(LocalDateTime now);
}

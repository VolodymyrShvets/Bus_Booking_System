package com.booking.system.service.mapper;

import com.booking.system.model.Bus;
import com.booking.system.model.dto.BusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusMapper {
    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    @Mapping(target = "name", source = "bus.name")
    @Mapping(target = "departureCity", source = "bus.departureCity")
    @Mapping(target = "arrivalCity", source = "bus.arrivalCity")
    @Mapping(target = "departureTime", source = "bus.departureTime")
    @Mapping(target = "arrivalTime", source = "bus.arrivalTime")
    @Mapping(target = "travelTime", source = "bus.travelTime")
    @Mapping(target = "availableSeats", source = "bus.availableSeats")
    @Mapping(target = "ticketPrice", source = "bus.ticketPrice")
    BusDTO busToBusDTO(Bus bus);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "departureCity", source = "dto.departureCity")
    @Mapping(target = "arrivalCity", source = "dto.arrivalCity")
    @Mapping(target = "departureTime", source = "dto.departureTime")
    @Mapping(target = "arrivalTime", source = "dto.arrivalTime")
    @Mapping(target = "travelTime", source = "dto.travelTime")
    @Mapping(target = "availableSeats", source = "dto.availableSeats")
    @Mapping(target = "ticketPrice", source = "dto.ticketPrice")
    Bus busDTOToBus(BusDTO dto);
}

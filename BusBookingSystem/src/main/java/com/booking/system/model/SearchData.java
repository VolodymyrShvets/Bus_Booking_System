package com.booking.system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class SearchData {
    private String departureCity;
    private String arrivalCity;
    private String departureDate;
}

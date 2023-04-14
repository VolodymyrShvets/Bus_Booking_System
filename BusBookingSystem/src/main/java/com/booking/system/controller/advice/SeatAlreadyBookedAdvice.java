package com.booking.system.controller.advice;

import com.booking.system.model.exception.SeatAlreadyBookedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class SeatAlreadyBookedAdvice {
    @ResponseBody
    @ExceptionHandler(SeatAlreadyBookedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String seatAlreadyBookedHandler(SeatAlreadyBookedException ex) {
        log.error("Following exception occurred {}: {}", ex.getClass(), ex.getMessage());
        return "error";
    }
}

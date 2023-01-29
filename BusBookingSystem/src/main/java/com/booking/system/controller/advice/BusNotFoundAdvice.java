package com.booking.system.controller.advice;

import com.booking.system.model.exception.BusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BusNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BusNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String busNotFoundHandler(BusNotFoundException ex) {
        return ex.getMessage();
    }
}

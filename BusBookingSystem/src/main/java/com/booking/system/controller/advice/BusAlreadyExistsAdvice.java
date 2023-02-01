package com.booking.system.controller.advice;

import com.booking.system.model.exception.BusAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BusAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(BusAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String busAlreadyExistsHandler(BusAlreadyExistsException ex) {
        return ex.getMessage();
    }
}

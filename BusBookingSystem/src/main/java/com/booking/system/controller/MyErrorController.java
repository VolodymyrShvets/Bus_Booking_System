package com.booking.system.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        //do something
        return "error";
    }
}

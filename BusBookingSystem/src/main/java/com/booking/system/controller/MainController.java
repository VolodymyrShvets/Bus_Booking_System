package com.booking.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/sign-in")
    public String login() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String register() {
        return "register";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}

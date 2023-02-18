package com.booking.system.controller;

import com.booking.system.model.dto.UserDTO;
import com.booking.system.service.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService service;

    @PostMapping(value = "/sign-up")
    public String registerNewUser(@ModelAttribute("user") UserDTO userDTO) {
        service.registerNewUser(userDTO);
        return "redirect:/sign-up?success=true";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user/{email}")
    @ModelAttribute("user")
    public UserDTO getUser(@PathVariable String email) {
        return service.getUser(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/user/all")
    @ModelAttribute("users")
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/user")
    public UserDTO updateUser(@ModelAttribute("user") UserDTO userDTO) {
        return service.updateUser(userDTO);
    }

    @DeleteMapping(value = "/user/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sign-up")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", userRegistrationDto());
        return "register";
    }

    public UserDTO userRegistrationDto() {
        return new UserDTO();
    }
}

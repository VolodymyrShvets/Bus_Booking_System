package com.booking.system.controller;

import com.booking.system.model.dto.UserDTO;
import com.booking.system.service.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDTO registerNewUser(@RequestBody UserDTO userDTO) {
        return service.registerNewUser(userDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}")
    public UserDTO getUser(@PathVariable String email) {
        return service.getUser(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all")
    public List<UserDTO> getAllUsers() {
        return service.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return service.updateUser(userDTO);
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}

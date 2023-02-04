package com.booking.system.service.api;

import com.booking.system.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO registerNewUser(UserDTO userDTO);

    UserDTO getUser(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(String email);
}

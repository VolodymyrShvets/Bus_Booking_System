package com.booking.system.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

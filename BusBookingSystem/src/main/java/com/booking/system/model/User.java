package com.booking.system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@Document
public class User {
    @Id
    String id;
    String firstName;
    String lastName;
    @Indexed(unique = true)
    String email;
    String password;
    //@DocumentReference(collection = "ticket", lookup = "{'userEmail' : ?#{#self.email} }")
    //List<Ticket> tickets;
}

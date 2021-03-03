package com.codelens.microservices.usersservice.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserCreatedEvent {

    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;

    public UserCreatedEvent(UUID userId, String name, String address, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}

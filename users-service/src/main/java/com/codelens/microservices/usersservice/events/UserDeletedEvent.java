package com.codelens.microservices.usersservice.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDeletedEvent {
    private UUID userId;

    public UserDeletedEvent(UUID userId) {
        this.userId = userId;
    }
}

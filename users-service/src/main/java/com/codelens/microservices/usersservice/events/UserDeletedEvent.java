package com.codelens.microservices.usersservice.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class UserDeletedEvent {
    @TargetAggregateIdentifier
    private UUID userId;
    private Boolean isDeleted;

    public UserDeletedEvent(UUID userId, Boolean isDeleted) {
        this.userId = userId;
        this.isDeleted = isDeleted;
    }
}

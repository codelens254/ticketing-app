package com.codelens.microservices.usersservice.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;
}

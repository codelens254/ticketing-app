package com.codelens.microservices.usersservice.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    private UUID userId;
}

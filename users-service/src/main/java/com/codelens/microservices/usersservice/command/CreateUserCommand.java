package com.codelens.microservices.usersservice.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateUserCommand {
    @RoutingKey
    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;
}

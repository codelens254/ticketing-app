package com.codelens.microservices.usersservice.command;

import com.codelens.microservices.usersservice.coreapi.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Slf4j
public class UserAggregate {

    @AggregateIdentifier
    private UUID userId;

    public UserAggregate() {
        // Required by Axon ...
    }


    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        userId = UUID.randomUUID();
        log.info("Creating user command ...");

        AggregateLifecycle.apply(new UserCreatedEvent(userId,
                createUserCommand.getName(),
                createUserCommand.getAddress(),
                createUserCommand.getPhoneNumber()));
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {

        AggregateLifecycle.apply(new UserUpdatedEvent(
                userId,
                updateUserCommand.getName(),
                updateUserCommand.getAddress(),
                updateUserCommand.getPhoneNumber()));
    }

    @CommandHandler
    public void on(DeleteUserCommand deleteUserCommand) throws UserDoesNotExistsException {
        if (deleteUserCommand.getUserId() == null)
            throw new UserDoesNotExistsException();
        AggregateLifecycle.apply(new UserDeletedEvent(userId));
    }


}

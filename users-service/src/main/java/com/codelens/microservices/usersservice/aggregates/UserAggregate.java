package com.codelens.microservices.usersservice.aggregates;

import com.codelens.microservices.usersservice.command.CreateUserCommand;
import com.codelens.microservices.usersservice.command.DeleteUserCommand;
import com.codelens.microservices.usersservice.command.UpdateUserCommand;
import com.codelens.microservices.usersservice.entities.UserEntity;
import com.codelens.microservices.usersservice.events.UserCreatedEvent;
import com.codelens.microservices.usersservice.events.UserDeletedEvent;
import com.codelens.microservices.usersservice.events.UserUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Slf4j
@Getter
@AllArgsConstructor
public class UserAggregate {

    @AggregateIdentifier
    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isDeleted;


    public UserAggregate() {
        // Required by Axon ...
    }


    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        log.info("Creating user command ...");
        userId = createUserCommand.getUserId();
        name = createUserCommand.getName();
        address = createUserCommand.getAddress();
        phoneNumber = createUserCommand.getPhoneNumber();

        AggregateLifecycle.apply(new UserCreatedEvent(userId,
                name,
                address,
                phoneNumber));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        userId = userCreatedEvent.getUserId();
        name = userCreatedEvent.getName();
        address = userCreatedEvent.getAddress();
        phoneNumber = userCreatedEvent.getPhoneNumber();
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {
        log.info("Handling update user command ...");
        AggregateLifecycle.apply(new UserUpdatedEvent(
                userId,
                updateUserCommand.getName(),
                updateUserCommand.getAddress(),
                updateUserCommand.getPhoneNumber()));
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        log.info(String.format("Handling user updated event sourcing...%s", userUpdatedEvent.getUserId()));
        userId = userUpdatedEvent.getUserId();
        name = userUpdatedEvent.getName();
        address = userUpdatedEvent.getAddress();
        phoneNumber = userUpdatedEvent.getPhoneNumber();
    }

    @CommandHandler
    public void on(DeleteUserCommand deleteUserCommand) {
        this.userId = deleteUserCommand.getUserId();
        // this is where all the business logic should happen ...
        AggregateLifecycle.apply(new UserDeletedEvent(userId, true));
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        userId = userDeletedEvent.getUserId();
        isDeleted = userDeletedEvent.getIsDeleted();
    }
}

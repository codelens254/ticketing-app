package com.codelens.microservices.usersservice.query;


import com.codelens.microservices.usersservice.coreapi.*;
import com.codelens.microservices.usersservice.entities.UserEntity;
import com.codelens.microservices.usersservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserProjector {

    private final UserRepository userRepository;

    public UserProjector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventSourcingHandler // will handle @userCreatedEvent for this aggregate's lifecycle ...
    public void on(UserCreatedEvent userCreatedEvent) {
        UUID userId = userCreatedEvent.getUserId();
        log.info(String.format("Handling user created event ..%s", userId));

        UserEntity user = new UserEntity(userId,
                userCreatedEvent.getName(),
                userCreatedEvent.getAddress(),
                userCreatedEvent.getPhoneNumber());
        userRepository.save(user);
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        log.info(String.format("Handling user updated event...%s", userUpdatedEvent.getUserId()));
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        log.info(String.format("Handling user deleted event...%s", userDeletedEvent.getUserId()));
    }

    @QueryHandler
    public UserEntity handle(FindUserQuery findUserQuery) {
        return userRepository.findById(findUserQuery.getUserId())
                .orElse(null);
    }
}

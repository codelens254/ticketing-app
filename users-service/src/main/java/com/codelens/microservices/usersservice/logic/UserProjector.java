package com.codelens.microservices.usersservice.logic;


import com.codelens.microservices.usersservice.entities.UserEntity;
import com.codelens.microservices.usersservice.events.UserCreatedEvent;
import com.codelens.microservices.usersservice.events.UserDeletedEvent;
import com.codelens.microservices.usersservice.events.UserUpdatedEvent;
import com.codelens.microservices.usersservice.query.FindAllUsersQuery;
import com.codelens.microservices.usersservice.query.FindUserQuery;
import com.codelens.microservices.usersservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class UserProjector {

    private final UserRepository userRepository;

    public UserProjector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventSourcingHandler
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

    @QueryHandler
    public List<UserEntity> handle(FindAllUsersQuery findAllUsersQuery) {
        return userRepository.findAll();
    }
}

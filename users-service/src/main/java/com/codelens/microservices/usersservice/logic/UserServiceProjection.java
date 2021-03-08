package com.codelens.microservices.usersservice.logic;


import amqp.BrokerProducer;
import amqp.BrokerProducerImpl;
import com.codelens.microservices.usersservice.entities.UserEntity;
import com.codelens.microservices.usersservice.events.UserCreatedEvent;
import com.codelens.microservices.usersservice.events.UserDeletedEvent;
import com.codelens.microservices.usersservice.events.UserUpdatedEvent;
import com.codelens.microservices.usersservice.query.FindAllUsersQuery;
import com.codelens.microservices.usersservice.query.FindUserQuery;
import com.codelens.microservices.usersservice.repository.UserRepository;
import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static Utils.Constants.TICKETING_USERS_QUEUE;

@Component
@Slf4j
public class UserServiceProjection {

    private final UserRepository userRepository;

    private final BrokerProducer<UserDto> userDtoBrokerProducer;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserServiceProjection(UserRepository userRepository,
                                 @Lazy BrokerProducer<UserDto> userDtoBrokerProducer,
                                 RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userDtoBrokerProducer = userDtoBrokerProducer;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    public BrokerProducer<UserDto> getUserDtoBrokerProducer() {
        return new BrokerProducerImpl<>(rabbitTemplate);
    }


    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        UUID userId = userCreatedEvent.getUserId();
        log.info(String.format("Handling user created event ..%s", userId));

        UserEntity user = new UserEntity(userId,
                userCreatedEvent.getName(),
                userCreatedEvent.getAddress(),
                userCreatedEvent.getPhoneNumber(),
                false);
        userRepository.save(user);

        // publish user created to the broker ...

        UserDto userDto = new UserDto(userId,
                userCreatedEvent.getName(),
                userCreatedEvent.getAddress(),
                userCreatedEvent.getPhoneNumber());

        userDtoBrokerProducer.sendPayloadToBroker(userDto, TICKETING_USERS_QUEUE);
    }

    @EventHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        log.info(String.format("Handling user updated event...%s", userUpdatedEvent.getUserId()));

        userRepository.findById(userUpdatedEvent.getUserId())
                .ifPresent(userEntity -> {
                    userEntity.setAddress(userUpdatedEvent.getAddress());
                    userEntity.setName(userUpdatedEvent.getName());
                    userEntity.setPhoneNumber(userUpdatedEvent.getPhoneNumber());
                    userRepository.save(userEntity);
                });
    }

    @EventHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        log.info(String.format("Handling user deleted event...%s", userDeletedEvent.getUserId()));

        userRepository.findById(userDeletedEvent.getUserId())
                .ifPresent(userEntity -> {
                    userEntity.setIsDeleted(true);
                    userRepository.save(userEntity);
                });

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

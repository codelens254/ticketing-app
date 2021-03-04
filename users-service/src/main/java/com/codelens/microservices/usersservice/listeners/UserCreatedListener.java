package com.codelens.microservices.usersservice.listeners;

import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static Utils.Constants.TICKETING_USERS_QUEUE;

@Component
@Slf4j
public class UserCreatedListener {

    @RabbitListener(queues = TICKETING_USERS_QUEUE)
    public void listen(UserDto userDto){
        log.info(userDto.toString());
    }
}

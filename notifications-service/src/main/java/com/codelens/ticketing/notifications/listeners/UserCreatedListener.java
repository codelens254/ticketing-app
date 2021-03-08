package com.codelens.ticketing.notifications.listeners;

import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static Utils.Constants.TICKETING_USERS_QUEUE;

@Component
@Slf4j
public class UserCreatedListener {

    @RabbitListener(queues = TICKETING_USERS_QUEUE)
    public void listen(UserDto userDto) {
        // todo :- implement your preferred SMS or EMAIL Notification logic .... e.g Twilio, SendGrid
        // create this user on the notifications Database ...
        log.info(String.format("Notification Service Event Listener: %s", userDto.toString()));
    }
}

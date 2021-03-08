package amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static Utils.Constants.TICKETING_USERS_QUEUE;

@Slf4j
@Service
public class BrokerProducerImpl<T> implements BrokerProducer<T> {

    private final RabbitTemplate rabbitTemplate;

    public BrokerProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendPayloadToBroker(T payload, String destination) {
        log.info(String.format("Sending payload '%s' to '%s' queue", payload.toString(), destination));
        rabbitTemplate.convertAndSend(destination, payload);
    }


    // Defining our queue bean ...
    @Bean
    public Queue usersQueue() {
        // we make the queue durable so that even when RabbitMq is stopped, any messages on it will not be removed ...
        return new Queue(TICKETING_USERS_QUEUE, true);
    }
}

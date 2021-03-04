package amqp;

public interface BrokerProducer<T> {

    void sendPayloadToBroker(T payload, String destination);
}

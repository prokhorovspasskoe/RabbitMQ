package ru.prokhorov.subscriber;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Subscriber {
    private static final String EXCHANGE_NAME = "topic_exchanger";
    private final String queueName;
    private final Channel channel;

    public Subscriber() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            queueName = channel.queueDeclare().getQueue();
            System.out.println("QUEUE NAME: " + queueName);

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRoutingKeyAndBind(String routingKey) {
        try {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" [*] Waiting for messages with routing key (" + routingKey + "):");
    }

    public void driverCallback() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
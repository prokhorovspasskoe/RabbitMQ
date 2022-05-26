package ru.prokhorov.itblog;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Blogger {
    private static final String EXCHANGE_NAME = "topic_exchanger";
    private Channel channel;
    private String routingKey;
    private String message;

    public Blogger() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRoutingKeyAndMessage(String routingKey, String message) {
        try {
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
    }
}

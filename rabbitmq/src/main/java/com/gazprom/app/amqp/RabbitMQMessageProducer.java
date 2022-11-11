package com.gazprom.app.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Sending message to exchange: {}, routingKey: {}, payload: {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Message sent to exchange: {}, routingKey: {}, payload: {}", exchange, routingKey, payload);
    }

    public Object publishAndGetResponse(Object payload, String exchange, String routingKey) {
        log.info("Sending message to exchange: {}, routingKey: {}, payload: {}", exchange, routingKey, payload);
        return amqpTemplate.convertSendAndReceive(exchange, routingKey, payload);
    }
}
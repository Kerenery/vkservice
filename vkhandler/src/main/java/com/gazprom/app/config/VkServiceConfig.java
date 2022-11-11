package com.gazprom.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VkServiceConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.vk-service}")
    private String vkQueue;

    @Value("${rabbitmq.routing-keys.internal-vk-service}")
    private String internalNotificationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue vkQueue() {
        return new Queue(this.vkQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(vkQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }


    public String getInternalExchange() {
        return internalExchange;
    }

    public String getVkQueue() {
        return vkQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }
}

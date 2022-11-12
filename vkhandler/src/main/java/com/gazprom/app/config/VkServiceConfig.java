package com.gazprom.app.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
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

    @Value("${vk.app.token}")
    private String vkToken;

    @Value("${vk.app.app-id}")
    private Integer vkApplicationId;

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

    public String getVkToken() {
        return vkToken;
    }

    public Integer getVkApplicationId() {
        return vkApplicationId;
    }

    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

    @Bean
    public ServiceActor serviceActor() {
        return new ServiceActor(vkApplicationId, vkToken);
    }
}

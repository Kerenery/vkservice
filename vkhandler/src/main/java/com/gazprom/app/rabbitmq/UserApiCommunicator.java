package com.gazprom.app.rabbitmq;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.VkHandleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserApiCommunicator {

    private final VkHandleService vkHandleService;

    @RabbitListener(queues = "${rabbitmq.queues.vk-service}")
    public VkUserDto receiveMessage(VkRequest vkRequest) {
        log.info("Received message from queue: {}", vkRequest);
        return vkHandleService.handleRequest(vkRequest);
    }
}

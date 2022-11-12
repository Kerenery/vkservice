package com.gazprom.app.service.impl;

import com.gazprom.app.config.VkServiceConfig;
import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.VkHandleService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.IsMemberResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Configurable
@AllArgsConstructor
public class VkHandleServiceImpl implements VkHandleService {
    private final VkApiClient vkApiClient;

    private final ServiceActor actor;

    public VkUserDto getUserById(Integer id) {
        GetResponse user;
        try {
            user = vkApiClient.users().get(actor).userIds(String.valueOf(id)).execute().get(0);
        } catch (ApiException | ClientException e) {
            log.info("Error while getting user by id: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return VkUserDto.builder()
                .id(Long.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public VkUserDto isMember(Integer vkUserId, Integer groupId) {
        var user = getUserById(vkUserId);

        if (user == null) {
            log.error("User with id {} not found", vkUserId);
            return null;
        }

        IsMemberResponse response;
        try {
            response = vkApiClient
                    .groups()
                    .isMember(actor, String.valueOf(groupId))
                    .userId(vkUserId)
                    .execute();
        } catch (ApiException | ClientException e) {
            log.info("Error while checking user membership, {}, invoked by {}", e.getMessage(), e.getClass());
            throw new RuntimeException(e);
        }

        return VkUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isMember(response.getValue().equals("1"))
                .build();
    }

    @Override
    public VkUserDto handleRequest(VkRequest vkRequest) {
        if (vkRequest.vkUserId() == null) {
            log.info("vkUserId is null");
            throw new RuntimeException("Invalid request");
        }
        if (vkRequest.groupId() == null) {
            log.info("groupId is null, handle as getUserById");
            return getUserById(vkRequest.vkUserId());
        } else {
            log.info("userId, groupId are not null, handle as isMember");
            return isMember(vkRequest.vkUserId(), vkRequest.groupId());
        }
    }
}
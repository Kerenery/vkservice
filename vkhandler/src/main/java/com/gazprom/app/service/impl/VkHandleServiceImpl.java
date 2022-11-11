package com.gazprom.app.service.impl;

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
import org.springframework.stereotype.Service;

@Service
public class VkHandleServiceImpl implements VkHandleService {
    private TransportClient transportClient;
    private VkApiClient vkApiClient;

    private ServiceActor actor;

    public VkHandleServiceImpl() {
        transportClient = new HttpTransportClient();
        vkApiClient = new VkApiClient(transportClient);
        actor = new ServiceActor(51471859, "d168291bd168291bd168291b53d2794ce8dd168d168291bb203e7c0a4489fbe109f14b1");
    }

    public VkUserDto getUserById(Integer id) {
        GetResponse user = null;
        try {
            user = vkApiClient.users().get(actor).userIds(String.valueOf(id)).execute().get(0);
        } catch (ApiException | ClientException e) {
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
        IsMemberResponse response;
        try {
            response = vkApiClient
                    .groups()
                    .isMember(actor, String.valueOf(groupId))
                    .userId(vkUserId)
                    .execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        return VkUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isMember(Boolean.valueOf(response.getValue()))
                .build();
    }

    @Override
    public VkUserDto handleRequest(VkRequest vkRequest) {
        if (vkRequest.vkUserId() == null) {
            throw new RuntimeException("Invalid request");
        }
        if (vkRequest.groupId() == null) {
            return getUserById(vkRequest.vkUserId());
        } else {
            return isMember(vkRequest.vkUserId(), vkRequest.groupId());
        }
    }

}

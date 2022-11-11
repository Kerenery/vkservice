package com.gazprom.app.service;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.request.VkRequest;

public interface VkHandleService {
    VkUserDto getUserById(Integer id);
    VkUserDto isMember(Integer vkUserId, Integer groupId);

    VkUserDto handleRequest(VkRequest vkRequest);
}

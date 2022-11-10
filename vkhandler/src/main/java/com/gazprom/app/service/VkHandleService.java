package com.gazprom.app.service;

import com.gazprom.app.entity.VkUser;

public interface VkHandleService {
    VkUser getUserById(Integer id);
    VkUser isMember(Integer vkUserId, Integer groupId);
}

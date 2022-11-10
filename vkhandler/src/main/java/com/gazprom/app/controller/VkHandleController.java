package com.gazprom.app.controller;


import com.gazprom.app.entity.VkUser;
import com.gazprom.app.service.VkHandleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class VkHandleController {

    private final VkHandleService vkHandleService;

    @GetMapping("/{userId}")
    public VkUser GetVkUserById(@PathVariable Integer userId) {
        return vkHandleService.getUserById(userId);
    }

    @GetMapping("/isMember/{vkUserId}/{groupId}")
    public VkUser isMember(@PathVariable Integer vkUserId, @PathVariable Integer groupId) {
        return vkHandleService.isMember(vkUserId, groupId);
    }
}
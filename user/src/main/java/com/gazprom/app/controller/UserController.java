package com.gazprom.app.controller;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping()
    public VkUserDto getVkUser(@RequestBody VkRequest vkRequest) {
        log.info("Received request to get user with id: {}", vkRequest);
        return userService.getVkUserById(vkRequest);
    }
}

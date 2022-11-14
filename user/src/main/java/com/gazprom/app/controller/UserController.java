package com.gazprom.app.controller;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.request.RegistrationPayload;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.impl.UserServiceImpl;
import com.gazprom.app.tool.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/vk")
    public ResponseEntity<?> getVkUser(@RequestBody VkRequest vkRequest) {
        log.info("Received request to get user with id: {}", vkRequest);
        return new ResponseEntity<>(userService.getVkUserById(vkRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/vk/membership")
    public ResponseEntity<?> getVkUserMembership(@RequestBody VkRequest vkRequest) {
        log.info("Received request to get user membership with id: {}", vkRequest);
        return new ResponseEntity<>(userService.getMembership(vkRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationPayload registrationPayload) throws UserNotFoundException {
        userService.addUser(registrationPayload);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

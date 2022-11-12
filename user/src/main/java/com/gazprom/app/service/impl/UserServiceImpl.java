package com.gazprom.app.service.impl;

import com.gazprom.app.amqp.RabbitMQMessageProducer;
import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.entity.AppUser;
import com.gazprom.app.enums.UserRole;
import com.gazprom.app.repository.UserRepository;
import com.gazprom.app.request.RegistrationPayload;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.UserService;
import com.gazprom.app.tool.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        log.info("User with username {} found", username);
        return user;
    }

    private void checkUser(AppUser appUser) {
        if (appUser.getUsername() == null || appUser.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is empty");
        }
        if (appUser.getPassword() == null || appUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }
    }

    @Override
    public AppUser addUser(RegistrationPayload registrationPayload) throws UserNotFoundException {
        AppUser appUser = AppUser.builder()
                .username(registrationPayload.username())
                .password(passwordEncoder.encode(registrationPayload.password()))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .build();

        checkUser(appUser);
        AppUser user = userRepository.findByUsername(appUser.getUsername());
        if (user != null) {
            throw new UserNotFoundException(appUser.getUsername());
        }

        appUser.setRole(UserRole.USER);
        return userRepository.save(appUser);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(value = "appuser", key = "#id")
    public AppUser getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with %s not found", id)));
    }

    @Override
    @CachePut(value = "appuser", key = "#appUser.id")
    public void updateUser(AppUser appUser) {
        checkUser(appUser);
        AppUser user = userRepository.findByUsername(appUser.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with %s not found", appUser.getUsername()));
        }
        user.setPassword(appUser.getPassword());
        userRepository.save(user);
    }

    @Override
    @CacheEvict(value = "appuser", key = "#id")
    public void removeUser(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", id)));
        userRepository.delete(user);
    }

    @Override
    @Cacheable(value = "vkUser", key = "#vkRequest.vkUserId()")
    public VkUserDto getVkUserById(VkRequest vkRequest) {
        if (vkRequest.vkUserId() == null) {
            throw new IllegalArgumentException("VkId is empty");
        }
        var user = rabbitMQMessageProducer.
                publishAndGetResponse(vkRequest,  "internal.exchange", "internal.vk-service.routing-key");
        return (VkUserDto) user;
    }

    @Override
    @Cacheable(value = "vkUser", key = "#vkRequest.vkUserId()")
    public VkUserDto getMembership(VkRequest vkRequest) {
        if (vkRequest.vkUserId() == null || vkRequest.groupId() == null) {
            throw new IllegalArgumentException("VkId or groupId id empty: " + vkRequest);
        }
        var user = rabbitMQMessageProducer.
                publishAndGetResponse(vkRequest,  "internal.exchange", "internal.vk-service.routing-key");
        return (VkUserDto) user;
    }
}

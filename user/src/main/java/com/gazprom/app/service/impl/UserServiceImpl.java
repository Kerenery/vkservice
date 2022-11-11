package com.gazprom.app.service.impl;

import com.gazprom.app.amqp.RabbitMQMessageProducer;
import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.entity.AppUser;
import com.gazprom.app.repository.UserRepository;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(String.format("User with %s not found", username));
        }

        return user;
    }

    @Override
    public AppUser addUser(AppUser appUser) {
        return null;
    }

    @Override
    public List<AppUser> getAllUsers() {
        return null;
    }

    @Override
    public AppUser getUserById(Long id) {
        return null;
    }

    @Override
    public void updateUser(AppUser appUser) {

    }

    @Override
    public void removeUser(Long id) {

    }

    @Override
    public VkUserDto getVkUserById(VkRequest vkRequest) {
        var user = rabbitMQMessageProducer.
                publishAndGetResponse(vkRequest,  "internal.exchange", "internal.vk-service.routing-key");
        return (VkUserDto) user;
    }

    @Override
    public VkUserDto getMembership(VkRequest vkRequest) {
        return null;
    }
}

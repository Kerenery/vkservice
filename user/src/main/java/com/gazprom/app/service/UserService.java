package com.gazprom.app.service;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.entity.AppUser;
import com.gazprom.app.request.RegistrationPayload;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.tool.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    AppUser addUser(RegistrationPayload appUser) throws UserNotFoundException;
    List<AppUser> getAllUsers();
    AppUser getUserById(Long id) throws UserNotFoundException;
    void updateUser(AppUser appUser);
    void removeUser(Long id);

    VkUserDto getVkUserById(VkRequest vkRequest);
    VkUserDto getMembership(VkRequest vkRequest);
}

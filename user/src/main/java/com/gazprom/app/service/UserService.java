package com.gazprom.app.service;

import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.entity.AppUser;
import com.gazprom.app.request.VkRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    AppUser addUser(AppUser appUser);
    List<AppUser> getAllUsers();
    AppUser getUserById(Long id);
    void updateUser(AppUser appUser);
    void removeUser(Long id);

    VkUserDto getVkUserById(VkRequest vkRequest);
    VkUserDto getMembership(VkRequest vkRequest);
}

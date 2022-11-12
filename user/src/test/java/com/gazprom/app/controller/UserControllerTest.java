package com.gazprom.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gazprom.app.dto.VkUserDto;
import com.gazprom.app.entity.AppUser;
import com.gazprom.app.enums.UserRole;
import com.gazprom.app.repository.UserRepository;
import com.gazprom.app.request.VkRequest;
import com.gazprom.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class,
                SecurityAutoConfiguration.class
        }
)
class UserControllerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        given(this.userService.loadUserByUsername(any()))
                .willReturn(AppUser.builder()
                        .role(UserRole.USER)
                        .id(1L)
                        .username("test")
                        .password("test")
                        .isAccountNonExpired(true)
                        .isAccountNonLocked(true)
                        .isEnabled(true)
                        .build());

        given(this.userService.getMembership(any(VkRequest.class)))
                .willReturn(VkUserDto
                        .builder()
                        .firstName("Pavel")
                        .lastName("Durov")
                        .isMember(false).build());
    }

    @Test
    public void testTryToGetUnauthorized() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/v1/users/vk/membership")
                                .content(objectMapper.writeValueAsBytes(new VkRequest(1, 1)))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void testGetMembership_BadRequest() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/v1/users/vk/membership")
                                .content(objectMapper.writeValueAsBytes(new VkRequest(1, 1)))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testGetAllUsers_WrongRole_isForbidden() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/v1/users/all")
                                .content(objectMapper.writeValueAsBytes(new VkRequest(1, 1)))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}
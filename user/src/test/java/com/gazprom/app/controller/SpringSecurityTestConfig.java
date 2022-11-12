package com.gazprom.app.controller;

import com.gazprom.app.entity.AppUser;
import com.gazprom.app.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
@Slf4j
public class SpringSecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        AppUser registeredUser = AppUser
                .builder()
                .password("test")
                .username("test")
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .id(1L)
                .role(UserRole.USER)
                .build();

        AppUser adminUser = AppUser
                .builder()
                .password("admin")
                .username("admin")
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .id(2L)
                .role(UserRole.ADMIN)
                .build();

        log.info("UserDetailsService bean created");

        return new InMemoryUserDetailsManager(Arrays.asList(
                registeredUser, adminUser
        ));
    }
}

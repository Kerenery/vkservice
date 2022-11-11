package com.gazprom.app.repository;

import com.gazprom.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}

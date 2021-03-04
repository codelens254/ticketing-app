package com.codelens.microservices.usersservice.repository;

import com.codelens.microservices.usersservice.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}

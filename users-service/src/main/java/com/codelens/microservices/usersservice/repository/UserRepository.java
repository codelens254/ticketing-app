package com.codelens.microservices.usersservice.repository;

import com.codelens.microservices.usersservice.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
}

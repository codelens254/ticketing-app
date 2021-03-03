package com.codelens.microservices.usersservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private UUID userId;
    private String name;
    private String address;
    private String phoneNumber;

    public UserEntity(UUID userId, String name, String address, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}

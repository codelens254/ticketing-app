package com.codelens.microservices.usersservice.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FindUserQuery {
    private UUID userId;

    public FindUserQuery(UUID userId) {
        this.userId = userId;
    }
}

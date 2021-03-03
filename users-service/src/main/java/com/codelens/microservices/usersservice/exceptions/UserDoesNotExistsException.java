package com.codelens.microservices.usersservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDoesNotExistsException extends Exception {
    private String message;

    public UserDoesNotExistsException(String message) {
        super(message);
    }
}

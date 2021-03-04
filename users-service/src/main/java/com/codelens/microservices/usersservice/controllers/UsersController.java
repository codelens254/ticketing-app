package com.codelens.microservices.usersservice.controllers;

import com.codelens.microservices.usersservice.command.CreateUserCommand;
import com.codelens.microservices.usersservice.command.DeleteUserCommand;
import com.codelens.microservices.usersservice.command.UpdateUserCommand;
import com.codelens.microservices.usersservice.entities.UserEntity;
import com.codelens.microservices.usersservice.query.FindAllUsersQuery;
import com.codelens.microservices.usersservice.query.FindUserQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UsersController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/create")
    public void handle(@RequestBody CreateUserCommand command) {
        command.setUserId(UUID.randomUUID());
        commandGateway.send(command);
    }

    @GetMapping("/{userId}")
    public CompletableFuture<UserEntity> getUser(@PathVariable String userId) {
        return queryGateway.query(new FindUserQuery(UUID.fromString(userId)),
                ResponseTypes.instanceOf(UserEntity.class));
    }

    @GetMapping("/all")
    public CompletableFuture<List<UserEntity>> getAllUsers() {
        return queryGateway.query(new FindAllUsersQuery(),
                ResponseTypes.multipleInstancesOf(UserEntity.class));
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UpdateUserCommand command){
        commandGateway.send(command);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        DeleteUserCommand command = new DeleteUserCommand(UUID.fromString(userId));
        commandGateway.send(command);
    }
}

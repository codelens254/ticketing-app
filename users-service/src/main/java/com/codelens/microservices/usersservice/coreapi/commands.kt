package com.codelens.microservices.usersservice.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

// Note we're using val ... this is because Commands should be immutable ...

data class CreateUserCommand(
        val name: String,
        val address: String,
        val phoneNumber: String
)

data class UpdateUserCommand(
        @TargetAggregateIdentifier
        val userId: UUID,
        val name: String,
        val address: String,
        val phoneNumber: String
)


data class DeleteUserCommand(
        @TargetAggregateIdentifier
        val userId: UUID
)
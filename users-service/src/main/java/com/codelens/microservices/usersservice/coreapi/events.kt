package com.codelens.microservices.usersservice.coreapi

import java.util.*

data class UserCreatedEvent(
        val userId: UUID,
        val name: String,
        val address: String,
        val phoneNumber: String
)

data class UserUpdatedEvent(
        val userId: UUID,
        val name: String,
        val address: String,
        val phoneNumber: String
)

data class UserDeletedEvent(
        val userId: UUID
)
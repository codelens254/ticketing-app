package com.codelens.microservices.usersservice.coreapi

import java.util.*

data class FindUserQuery(
        val userId: UUID
)

class FindAllUsersQuery
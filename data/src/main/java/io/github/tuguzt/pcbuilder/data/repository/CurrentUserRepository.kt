package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.domain.model.user.User

interface CurrentUserRepository {
    suspend fun getCurrentUser(): Result<User?, Error>

    suspend fun updateCurrentUser(currentUser: User?): Result<Unit, Error>
}

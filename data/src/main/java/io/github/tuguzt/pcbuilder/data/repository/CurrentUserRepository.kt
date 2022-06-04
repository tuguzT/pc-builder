package io.github.tuguzt.pcbuilder.data.repository

import io.github.tuguzt.pcbuilder.domain.model.user.User

interface CurrentUserRepository {
    suspend fun getCurrentUser(): User?

    suspend fun updateCurrentUser(currentUser: User?)
}

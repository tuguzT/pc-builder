package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.repository.CurrentUserRepository
import io.github.tuguzt.pcbuilder.domain.model.user.User

class CurrentUserRepositoryImpl : CurrentUserRepository {
    private var currentUser: User? = null

    override suspend fun getCurrentUser(): User? = currentUser

    override suspend fun updateCurrentUser(currentUser: User?) {
        this.currentUser = currentUser
    }
}

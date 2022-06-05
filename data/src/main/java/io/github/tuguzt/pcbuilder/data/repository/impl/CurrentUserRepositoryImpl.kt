package io.github.tuguzt.pcbuilder.data.repository.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.repository.CurrentUserRepository
import io.github.tuguzt.pcbuilder.data.success
import io.github.tuguzt.pcbuilder.domain.model.user.User

class CurrentUserRepositoryImpl : CurrentUserRepository {
    private var currentUser: User? = null

    override suspend fun getCurrentUser(): Result<User?, Error> = success(currentUser)

    override suspend fun updateCurrentUser(currentUser: User?): Result<Unit, Error> {
        this.currentUser = currentUser
        return success(Unit)
    }
}

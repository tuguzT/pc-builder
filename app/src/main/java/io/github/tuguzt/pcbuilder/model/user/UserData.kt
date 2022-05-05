package io.github.tuguzt.pcbuilder.model.user

import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import kotlinx.serialization.Serializable

/**
 * Represents data of the [user][User].
 */
@Serializable
data class UserData(
    override val id: String,
    override val role: UserRole,
    override val username: String,
    override val email: String?,
    override val imageUri: String?,
) : User {
    init {
        require(checkUsername(username)) { "Username must be valid" }
    }
}

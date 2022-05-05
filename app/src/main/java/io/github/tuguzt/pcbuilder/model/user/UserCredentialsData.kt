package io.github.tuguzt.pcbuilder.model.user

import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import kotlinx.serialization.Serializable

/**
 * Represents [user credentials][UserCredentials] data.
 */
@Serializable
data class UserCredentialsData(
    override val username: String,
    override val password: String,
) : UserCredentials {
    init {
        require(checkUsername(username)) { "Username must be valid" }
        require(checkPassword(password)) { "Password must be valid" }
    }
}

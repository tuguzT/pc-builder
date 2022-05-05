package io.github.tuguzt.pcbuilder.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import kotlinx.serialization.Serializable

/**
 * Represents [user credentials][UserCredentials] data.
 */
@Serializable
data class UserCredentialsData(
    override val username: String,
    override val password: String,
) : UserCredentials

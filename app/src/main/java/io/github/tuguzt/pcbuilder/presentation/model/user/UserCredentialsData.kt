package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentialsData(
    override val username: String,
    override val password: String,
) : UserCredentials

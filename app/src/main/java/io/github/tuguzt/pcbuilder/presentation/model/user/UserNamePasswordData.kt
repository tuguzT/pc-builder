package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import kotlinx.serialization.Serializable

@Serializable
data class UserNamePasswordData(
    val user: UserData,
    override val password: String,
) : User by user, UserNamePassword

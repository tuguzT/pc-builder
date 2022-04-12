package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    override val id: String = randomNanoId(),
    override val email: String?,
    @SerialName("image_uri") override val imageUri: String?,
    override val role: UserRole,
    override val username: String,
) : User

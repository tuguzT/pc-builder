package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class UserData(
    override val id: String = randomNanoId(),
    override val email: String?,
    @SerialName("image_uri") override val imageUri: String?,
    override val role: UserRole,
) : User {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as UserData
        return this.id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (imageUri?.hashCode() ?: 0)
        result = 31 * result + role.hashCode()
        return result
    }
}

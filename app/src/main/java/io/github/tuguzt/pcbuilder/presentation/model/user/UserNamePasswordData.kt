package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import kotlinx.serialization.Serializable

@Serializable
class UserNamePasswordData(
    val user: UserData,
    override val username: String,
    override val password: String,
) : UserData(user.id, user.email, user.imageUri, user.role), UserNamePassword {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as UserNamePasswordData
        return this.id == other.id
    }

    override fun hashCode(): Int {
        var result = user.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}

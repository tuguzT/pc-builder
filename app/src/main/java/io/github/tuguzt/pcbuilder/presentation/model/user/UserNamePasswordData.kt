package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class UserNamePasswordData(
    @Transient
    override val id: String = randomNanoId(),

    @Transient
    override val email: String? = null,

    @Transient
    override val imageUri: String? = null,

    @Transient
    override val role: UserRole = UserRole.User,

    override val username: String,

    override val password: String,
) : UserData(id, email, imageUri, role), UserNamePassword {

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        other as UserNamePasswordData
        return this.id == other.id
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}

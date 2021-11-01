package io.github.tuguzt.pcbuilder.presentation.model.user

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.github.tuguzt.pcbuilder.domain.model.user.User

/**
 * Base interface for users.
 */
sealed interface UserSealed : User {
    override val email: String
    val password: String
    override var imageUri: String?
}

inline val UserSealed.role get() = when (this) {
    Admin -> "admin"
    is Moderator -> "moderator"
    is UserOrdinal -> "ordinal"
}

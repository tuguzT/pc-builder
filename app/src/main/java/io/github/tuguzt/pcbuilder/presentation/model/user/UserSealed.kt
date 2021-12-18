package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserNamePassword

/**
 * Base interface for users.
 */
sealed interface UserSealed : UserNamePassword {
    override val email: String
    override val password: String
    override var imageUri: String?
}

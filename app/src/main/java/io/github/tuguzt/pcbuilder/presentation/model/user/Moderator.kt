package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.domain.randomNanoId

/**
 * Moderator of the application.
 *
 * @see UserSealed
 */
class Moderator internal constructor(
    override val id: String = randomNanoId(),
    override val username: String,
    override val email: String,
    override val password: String,
    override var imageUri: String?,
) : UserSealed {
    override val role = UserRole.Moderator
}

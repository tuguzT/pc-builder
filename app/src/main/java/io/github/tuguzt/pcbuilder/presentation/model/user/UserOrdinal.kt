package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.randomNanoId

/**
 * Ordinal user of the application.
 *
 * @see UserSealed
 */
class UserOrdinal internal constructor(
    override val id: String = randomNanoId(),
    override val username: String,
    override val email: String,
    override val password: String,
    override var imageUri: String?,
) : UserSealed

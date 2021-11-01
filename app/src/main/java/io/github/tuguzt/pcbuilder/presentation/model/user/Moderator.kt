package io.github.tuguzt.pcbuilder.presentation.model.user

import com.aventrix.jnanoid.jnanoid.NanoIdUtils

/**
 * Moderator of the application.
 *
 * @see UserSealed
 */
class Moderator internal constructor(
    override val id: String = NanoIdUtils.randomNanoId(),
    override val username: String,
    override val email: String,
    override val password: String,
    override var imageUri: String?,
) : UserSealed

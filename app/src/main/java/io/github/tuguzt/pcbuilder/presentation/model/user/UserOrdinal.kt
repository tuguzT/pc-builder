package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri

/**
 * Ordinal user of the application.
 *
 * @see User
 */
open class UserOrdinal internal constructor(
    override val username: String,
    override val email: String,
    override val password: String,
    override var imageUri: Uri?,
) : User

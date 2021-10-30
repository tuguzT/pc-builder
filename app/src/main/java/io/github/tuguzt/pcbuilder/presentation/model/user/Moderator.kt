package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri

/**
 * Moderator of the application.
 *
 * @see User
 */
class Moderator(
    override val username: String,
    override val email: String,
    override val password: String,
    override var imageUri: Uri?,
) : User

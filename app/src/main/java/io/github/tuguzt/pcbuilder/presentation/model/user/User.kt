package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri
import io.github.tuguzt.pcbuilder.domain.model.Identifiable

/**
 * Base interface for users.
 */
sealed interface User : Identifiable<String> {
    val username: String
    val email: String
    val password: String
    var imageUri: Uri?

    override val id get() = username
}

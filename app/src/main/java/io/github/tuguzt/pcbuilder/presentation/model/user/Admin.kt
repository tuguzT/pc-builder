package io.github.tuguzt.pcbuilder.presentation.model.user

import android.net.Uri

/**
 * Administrator of the application.
 *
 * @see User
 */
object Admin : User {
    override val username = "Тимур Тугушев"
    override val email = "timurka.tugushev@gmail.com"
    override val password = "admin"
    override var imageUri: Uri? = null
}

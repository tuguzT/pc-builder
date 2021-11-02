package io.github.tuguzt.pcbuilder.presentation.model.user

import io.github.tuguzt.pcbuilder.domain.model.randomNanoId

/**
 * Administrator of the application.
 *
 * @see UserSealed
 */
object Admin : UserSealed {
    override val id = randomNanoId()
    override val username = "Тимур Тугушев"
    override val email = "timurka.tugushev@gmail.com"
    override val password = "admin"
    override var imageUri: String? = null
}

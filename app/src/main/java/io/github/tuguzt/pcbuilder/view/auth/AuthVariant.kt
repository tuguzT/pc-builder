package io.github.tuguzt.pcbuilder.view.auth

import io.github.tuguzt.pcbuilder.domain.model.user.UserCredentials

/**
 * Represents possible authentication variants provided by the application.
 */
sealed interface AuthVariant {
    /**
     * Represents authentication by user [credentials].
     */
    data class Credentials(val credentials: UserCredentials) : AuthVariant

    /**
     * Represents authentication by Google.
     */
    object Google : AuthVariant
}

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth

import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername

data class AuthState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
)

inline val AuthState.isValid: Boolean
    get() = checkUsername(username) && checkPassword(password)

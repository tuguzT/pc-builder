package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth

import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class AuthState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val userMessages: List<UserMessage> = listOf(),
)

inline val AuthState.isValid: Boolean
    get() = checkUsername(username) && checkPassword(password)

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.auth

import io.github.tuguzt.pcbuilder.domain.interactor.checkPassword
import io.github.tuguzt.pcbuilder.domain.interactor.checkUsername
import io.github.tuguzt.pcbuilder.presentation.viewmodel.BackendErrorKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class AuthState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val userMessages: List<UserMessage<out AuthMessageKind>> = listOf(),
) : MessageState<AuthMessageKind>

inline val AuthState.isValid: Boolean
    get() = checkUsername(username) && checkPassword(password)

sealed interface AuthMessageKind : MessageKind {
    data class Backend(val backendErrorKind: BackendErrorKind) : AuthMessageKind {
        companion object {
            fun server(): Backend = Backend(BackendErrorKind.ServerError)
            fun network(): Backend = Backend(BackendErrorKind.NetworkError)
            fun unknown(): Backend = Backend(BackendErrorKind.UnknownError)
        }
    }

    object NoGoogleId : AuthMessageKind
}

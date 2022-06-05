package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.presentation.viewmodel.BackendErrorKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageKind
import io.github.tuguzt.pcbuilder.presentation.viewmodel.MessageState
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class AccountState(
    val currentUser: User? = null,
    val isLoading: Boolean = true,
    override val userMessages: List<UserMessage<out AccountMessageKind>> = listOf(),
) : MessageState<AccountMessageKind>

inline val AccountState.signedIn: Boolean
    get() = currentUser != null

sealed interface AccountMessageKind : MessageKind {
    data class Backend(val backendErrorKind: BackendErrorKind) : AccountMessageKind {
        companion object {
            fun server(): Backend = Backend(BackendErrorKind.ServerError)
            fun network(): Backend = Backend(BackendErrorKind.NetworkError)
            fun unknown(): Backend = Backend(BackendErrorKind.UnknownError)
        }
    }
}

package io.github.tuguzt.pcbuilder.presentation.viewmodel.root.main.account

import io.github.tuguzt.pcbuilder.domain.model.user.User
import io.github.tuguzt.pcbuilder.presentation.viewmodel.UserMessage

data class AccountState(
    val currentUser: User? = null,
    val isLoading: Boolean = true,
    val userMessages: List<UserMessage> = listOf(),
)

inline val AccountState.signedIn: Boolean
    get() = currentUser != null

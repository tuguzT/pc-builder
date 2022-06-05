package io.github.tuguzt.pcbuilder.presentation.viewmodel

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.NanoId

interface MessageState<T : MessageKind> {
    val userMessages: List<UserMessage<out T>>
}

interface MessageKind

data class UserMessage<T : MessageKind>(val kind: T, val id: NanoId = randomNanoId())

enum class BackendErrorKind : MessageKind {
    ServerError,
    NetworkError,
    UnknownError,
}

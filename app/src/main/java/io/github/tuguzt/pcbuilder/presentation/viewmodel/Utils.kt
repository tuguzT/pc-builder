package io.github.tuguzt.pcbuilder.presentation.viewmodel

import io.github.tuguzt.pcbuilder.domain.interactor.randomNanoId
import io.github.tuguzt.pcbuilder.domain.model.NanoId

data class UserMessage(val message: String, val id: NanoId = randomNanoId())

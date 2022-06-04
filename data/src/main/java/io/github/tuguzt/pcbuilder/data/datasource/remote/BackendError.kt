package io.github.tuguzt.pcbuilder.data.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class BackendError(val message: String)

package io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model

import kotlinx.serialization.Serializable

/**
 * Represents the source item from the [Attribution.sources].
 */
@Serializable
data class Source(
    val uid: String? = null,
    val name: String,
)

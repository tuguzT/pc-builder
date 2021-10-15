package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.Serializable

/**
 * Represents the source item from the [Attribution.sources].
 */
@Serializable
internal data class Source(
    val uid: String? = null,
    val name: String,
)

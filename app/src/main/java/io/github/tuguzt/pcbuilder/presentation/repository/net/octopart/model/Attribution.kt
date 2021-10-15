package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an attribution from the [ImageSet.attribution].
 */
@Serializable
internal data class Attribution(
    val sources: List<Source>,
    @SerialName("first_acquired") val firstAcquired: String? = null,
)

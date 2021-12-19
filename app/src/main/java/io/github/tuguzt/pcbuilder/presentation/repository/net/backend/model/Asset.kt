package io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model

import kotlinx.serialization.Serializable

/**
 * Represents an asset from the [image set][PartResult.Item.imageSets] item.
 */
@Serializable
data class Asset(
    val url: String,
    val mimetype: String,
    val metadata: Unit? = null,
)

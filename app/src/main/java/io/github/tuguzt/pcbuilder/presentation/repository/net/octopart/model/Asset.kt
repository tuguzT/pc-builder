package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.Serializable

/**
 * Represents an asset from the [image set][PartResult.Item.imageSet] item.
 */
@Serializable
internal data class Asset(
    val url: String,
    val mimetype: String,
    val metadata: Unit? = null,
)

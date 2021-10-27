package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an item from the list of [image sets][PartResult.Item.imageSets].
 */
@Serializable
data class ImageSet(
    @SerialName("swatch_image") val swatchImage: Asset? = null,
    @SerialName("small_image") val smallImage: Asset? = null,
    @SerialName("medium_image") val mediumImage: Asset? = null,
    @SerialName("large_image") val largeImage: Asset? = null,
    val attribution: Attribution,
    @SerialName("credit_string") val credits: String? = null,
    @SerialName("credit_url") val creditUrl: String? = null,
)

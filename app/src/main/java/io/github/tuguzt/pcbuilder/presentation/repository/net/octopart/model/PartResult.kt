package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data about the part from the [SearchResponse.results].
 */
@Serializable
internal data class PartResult(val item: Item) {
    @Serializable
    data class Item(
        val uid: String,
        val mpn: String,
        val brand: Brand,
        val manufacturer: Manufacturer,
        val specs: Map<String, PartSpecValue>? = null,
        @SerialName("short_description") val description: String,
        @SerialName("octopart_url") val url: String,
        val offers: List<PartOffer>,
        @SerialName("imagesets") val imageSet: List<ImageSet>,
    )
}

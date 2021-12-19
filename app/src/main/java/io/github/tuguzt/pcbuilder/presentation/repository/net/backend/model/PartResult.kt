package io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data about the part from the [SearchResponse.results].
 */
@Serializable
data class PartResult(val item: Item) {
    @Serializable
    data class Item(
        val uid: String,
        @SerialName("short_description") val description: String,
        @SerialName("imagesets") val imageSets: List<ImageSet>,
    )
}

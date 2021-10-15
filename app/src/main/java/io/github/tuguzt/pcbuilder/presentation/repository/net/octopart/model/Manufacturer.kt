package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents [manufacturer][PartResult.Item.manufacturer] field of [PartResult] response.
 */
@Serializable
internal data class Manufacturer(
    val uid: String,
    val name: String,
    @SerialName("homepage_url") val url: String? = null,
)

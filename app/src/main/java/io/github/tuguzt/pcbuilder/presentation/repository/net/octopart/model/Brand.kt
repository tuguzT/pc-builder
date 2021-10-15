package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents [brand][PartResult.Item.brand] field of [PartResult] response.
 */
@Serializable
internal data class Brand(
    val uid: String,
    val name: String,
    @SerialName("homepage_url") val url: String? = null,
)

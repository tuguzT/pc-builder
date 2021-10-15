package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representation of the seller from the [PartOffer.seller].
 */
@Serializable
internal data class Seller(
    val uid: String,
    val name: String,
    @SerialName("homepage_url") val url: String? = null,
    @SerialName("display_flag") val displayFlag: String? = null,
    @SerialName("has_ecommerce") val hasECommerce: Boolean? = null,
)

package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an item of offers from the [PartResult.Item.offers].
 */
@Serializable
internal data class PartOffer(
    val id: String,
    val sku: String,
    val seller: Seller,
    @SerialName("eligible_region") val eligibleRegion: String? = null,
    @SerialName("product_url") val productUrl: String? = null,
    @SerialName("octopart_rfq_url") val rfqUrl: String? = null,
    val prices: Map<String, List<List<Double>>>,
    @SerialName("in_stock_quantity") val stockQuantity: Int? = null,
    @SerialName("on_order_quantity") val orderQuantity: Int? = null,
    @SerialName("on_order_eta") val orderETA: String? = null,
    @SerialName("factory_lead_days") val factoryLeadDays: Int? = null,
    @SerialName("factory_order_multiple") val factoryOrderMultiple: Int? = null,
    @SerialName("order_multiple") val orderMultiple: Int? = null,
    @SerialName("moq") val minimalOrderQuantity: Int? = null,
    @SerialName("multipack_quantity") val multiPackQuantity: String? = null,
    val packaging: String? = null,
    @SerialName("is_authorized") val isAuthorized: Boolean,
    @SerialName("last_updated") val lastUpdated: String,
)

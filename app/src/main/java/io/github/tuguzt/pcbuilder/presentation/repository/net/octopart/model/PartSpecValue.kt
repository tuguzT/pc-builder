package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Specification value contained in the map of [PartResult.Item.specs].
 */
@Serializable
internal data class PartSpecValue(
    val value: List<String>,
    @SerialName("min_value") val minValue: String? = null,
    @SerialName("max_value") val maxValue: String? = null,
    val metadata: SpecMetadata? = null,
    val attribution: Attribution? = null,
)

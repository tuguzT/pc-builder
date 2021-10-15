package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI

/**
 * Response from the [OctopartAPI.searchQuery].
 */
@Serializable
internal data class SearchResponse(
    val hits: Int? = null,
    val results: List<PartResult>,
    val request: RequestData,
    @SerialName("msec") val responseTime: Int,
    @SerialName("facet_results") val facets: Unit,
    @SerialName("stats_results") val stats: Unit,
    @SerialName("spec_metadata") val metadata: Unit,
)

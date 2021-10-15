package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents [request][SearchResponse.request] field in POJO [SearchResponse].
 */
@Serializable
internal data class RequestData(
    @SerialName("q") val query: String,
    val start: Int,
    val limit: Int,
    val sort: String,
    @SerialName("sort_dir") val sortDirection: String,
    val filters: Unit,
    val country: String,
    val facet: String? = null,
    val stats: String? = null,
)

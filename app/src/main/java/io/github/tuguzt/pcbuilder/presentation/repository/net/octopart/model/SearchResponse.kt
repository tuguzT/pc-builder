package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import kotlinx.serialization.Serializable

/**
 * Response from the [OctopartAPI.searchQuery].
 */
@Serializable
data class SearchResponse(
    val hits: Int? = null,
    val results: List<PartResult>,
)

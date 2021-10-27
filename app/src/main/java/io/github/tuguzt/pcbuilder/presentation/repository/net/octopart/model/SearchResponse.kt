package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.Serializable
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI

/**
 * Response from the [OctopartAPI.searchQuery].
 */
@Serializable
data class SearchResponse(
    val hits: Int? = null,
    val results: List<PartResult>,
)

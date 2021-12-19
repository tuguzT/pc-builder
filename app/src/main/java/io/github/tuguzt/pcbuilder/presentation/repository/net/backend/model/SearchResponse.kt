package io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model

import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendOctopartAPI
import kotlinx.serialization.Serializable

/**
 * Response from the [BackendOctopartAPI.search].
 */
@Serializable
data class SearchResponse(
    val hits: Int? = null,
    val results: List<PartResult>,
)

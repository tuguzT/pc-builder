package io.github.tuguzt.pcbuilder.presentation.repository.net.backend.model

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.presentation.repository.net.backend.BackendOctopartAPI
import kotlinx.serialization.Serializable

/**
 * Represents the [result][SearchResponse] of searching query by [BackendOctopartAPI.search].
 */
@Serializable
data class SearchResult(private val partResult: PartResult) : Identifiable<String> {
    override val id get() = partResult.item.uid
    val description get() = partResult.item.description
    val images get() = partResult.item.imageSets
}

/**
 * Converts [SearchResponse] into list of [SearchResult].
 */
fun SearchResponse.toResults() = results.map { SearchResult(it) }

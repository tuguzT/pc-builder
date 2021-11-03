package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model.PartResult
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model.SearchResponse

/**
 * Represents the [result][SearchResponse] of searching query by [OctopartAPI.searchQuery].
 */
data class SearchResult(private val partResult: PartResult) : Identifiable<String> {
    override val id get() = partResult.item.uid
    val description get() = partResult.item.description
    val images get() = partResult.item.imageSets
}

/**
 * Converts [SearchResponse] into list of [SearchResult].
 */
fun SearchResponse.toResults() = results.map { SearchResult(it) }

package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model

import kotlinx.serialization.Serializable

/**
 * Represents the metadata of the [PartSpecValue].
 */
@Serializable
internal data class SpecMetadata(
    val key: String,
    val name: String,
    val datatype: String? = null,
    val unit: UnitOfMeasurement? = null,
) {
    @Serializable
    data class UnitOfMeasurement(
        val name: String,
        val symbol: String,
    )
}

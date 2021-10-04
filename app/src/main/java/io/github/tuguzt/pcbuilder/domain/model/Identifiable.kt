package io.github.tuguzt.pcbuilder.domain.model

/**
 * Represent objects which can be identified by ID.
 */
interface Identifiable<T> {
    val id: T
}

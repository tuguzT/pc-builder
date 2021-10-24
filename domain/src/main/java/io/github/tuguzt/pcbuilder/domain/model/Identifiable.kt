package io.github.tuguzt.pcbuilder.domain.model

/**
 * Represent objects which can be identified by ID.
 */
interface Identifiable<out T : Any> {
    val id: T
}

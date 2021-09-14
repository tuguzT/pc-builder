package com.mirea.tuguzt.pcbuilder.domain.model

/**
 * Base class for all PC components.
 */
open class Component(
    open val name: String,
    open val description: String,
    open val weight: Grams,
    open val size: Size,
)

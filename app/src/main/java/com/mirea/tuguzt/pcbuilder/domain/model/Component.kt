package com.mirea.tuguzt.pcbuilder.domain.model

import com.mirea.tuguzt.pcbuilder.domain.Grams

/**
 * Base interface for all PC components.
 */
interface Component {
    val name: String
    val description: String
    val weight: Grams
    val size: Size
}

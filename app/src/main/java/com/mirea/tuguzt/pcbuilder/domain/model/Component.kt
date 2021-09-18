package com.mirea.tuguzt.pcbuilder.domain.model

import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * Base interface for all PC components.
 */
interface Component {
    val name: String
    val description: String
    val weight: Measure<Mass>
    val size: Size
}

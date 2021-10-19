package io.github.tuguzt.pcbuilder.domain.model.component

import io.github.tuguzt.pcbuilder.domain.model.Identifiable
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Measure

/**
 * Base interface for all PC components.
 */
interface Component : Identifiable<String> {
    val name: String
    val description: String
    val weight: Measure<Mass>
    val size: Size
}

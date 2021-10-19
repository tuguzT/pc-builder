package io.github.tuguzt.pcbuilder.domain.model.component

import io.nacular.measured.units.Distance
import io.nacular.measured.units.Measure

/**
 * Data class that represents physical size of the PC component.
 */
data class Size(
    val length: Measure<Distance>,
    val width: Measure<Distance>,
    val height: Measure<Distance>,
)

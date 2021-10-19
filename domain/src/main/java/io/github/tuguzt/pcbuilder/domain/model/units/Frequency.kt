package io.github.tuguzt.pcbuilder.domain.model.units

import io.nacular.measured.units.InverseUnits
import io.nacular.measured.units.Time
import io.nacular.measured.units.Time.Companion.seconds
import io.nacular.measured.units.div

/**
 * Represents quantity of frequency.
 */
typealias Frequency = InverseUnits<Time>

val hertz: Frequency = (1 / seconds).units

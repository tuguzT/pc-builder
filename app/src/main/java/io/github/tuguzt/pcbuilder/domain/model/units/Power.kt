package io.github.tuguzt.pcbuilder.domain.model.units

import io.nacular.measured.units.*
import io.nacular.measured.units.Length.Companion.meters
import io.nacular.measured.units.Mass.Companion.kilograms
import io.nacular.measured.units.Time.Companion.seconds

/**
 * Represents quantity of power.
 */
typealias Power = UnitsRatio<UnitsProduct<Mass, Square<Length>>, UnitsProduct<Square<Time>, Time>>

val watt: Power = kilograms * (meters * meters) / (seconds * seconds * seconds)

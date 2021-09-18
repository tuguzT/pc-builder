package com.mirea.tuguzt.pcbuilder.domain.model.units

import com.mirea.tuguzt.pcbuilder.domain.model.units.LuminousIntensity.Companion.candela
import io.nacular.measured.units.*
import io.nacular.measured.units.Length.Companion.meters

/**
 * Represents quantity of luminance.
 */
typealias Luminance = UnitsRatio<LuminousIntensity, Square<Distance>>

val nit: Luminance = candela / (meters * meters)

package io.github.tuguzt.pcbuilder.domain.model.units

import io.nacular.measured.units.Units

/**
 * Represents quantity of luminous intensity.
 */
class LuminousIntensity(suffix: String, ratio: Double = 1.0) : Units(suffix, ratio) {
    companion object {
        val candela = LuminousIntensity("cd")
    }
}

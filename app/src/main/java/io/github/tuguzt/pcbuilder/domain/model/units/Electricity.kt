package io.github.tuguzt.pcbuilder.domain.model.units

import io.nacular.measured.units.Units

/**
 * Represents quantity of electric current.
 */
class Electricity(suffix: String, ratio: Double = 1.0) : Units(suffix, ratio) {
    companion object {
        val ampere = Electricity("A")
    }
}

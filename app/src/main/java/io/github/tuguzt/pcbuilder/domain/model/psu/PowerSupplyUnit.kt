package io.github.tuguzt.pcbuilder.domain.model.psu

import io.github.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all power supply units (PSU) of PC.
 *
 * @see PSU
 */
interface PowerSupplyUnit : Component {
    // todo
}

/**
 * Shorthand for power supply unit.
 *
 * @see PowerSupplyUnit
 */
typealias PSU = PowerSupplyUnit
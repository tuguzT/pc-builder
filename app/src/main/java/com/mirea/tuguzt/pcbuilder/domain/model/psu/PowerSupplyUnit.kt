package com.mirea.tuguzt.pcbuilder.domain.model.psu

import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all power supply units (PSU) of PC.
 */
interface PowerSupplyUnit : Component

/**
 * Shorthand for power supply unit.
 *
 * @see PowerSupplyUnit
 */
typealias PSU = PowerSupplyUnit

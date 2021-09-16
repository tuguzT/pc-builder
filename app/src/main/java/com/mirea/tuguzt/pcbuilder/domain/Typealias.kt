package com.mirea.tuguzt.pcbuilder.domain

import com.mirea.tuguzt.pcbuilder.domain.model.cpu.CentralProcessingUnit
import com.mirea.tuguzt.pcbuilder.domain.model.GraphicsProcessingUnit
import com.mirea.tuguzt.pcbuilder.domain.model.PowerSupplyUnit

/**
 * Representation of millimeters.
 */
typealias Millimeters = UInt

/**
 * Representation of nanometers.
 */
typealias Nanometers = UInt

/**
 * Representation of grams.
 */
typealias Grams = UInt

/**
 * Representation of pixels.
 */
typealias Pixels = UInt

/**
 * Representation of hertz (Hz).
 */
typealias Hertz = UInt

/**
 * Representation of megahertz (MHz).
 */
typealias MegaHertz = UInt

/**
 * Representation of gigahertz (GHz).
 */
typealias GigaHertz = Float

/**
 * Representation of a unit of power in watts.
 */
typealias Watt = UInt

/**
 * Representation of milliseconds.
 */
typealias Milliseconds = Float

/**
 * Representation of non-SI unit of **luminance**
 * (SI unit is called *candela per square metre* and is equal to nit).
 */
typealias Nit = UInt

/**
 * Representation of voltage in volts.
 */
typealias Volt = Float

/**
 * Representation of memory capacity in megabytes (MB).
 */
typealias Megabytes = UInt

/**
 * Representation of memory capacity in gigabytes (GB).
 */
typealias Gigabytes = UInt

/**
 * Shorthand for central processing unit.
 *
 * @see CentralProcessingUnit
 */
typealias CPU = CentralProcessingUnit

/**
 * Shorthand for graphics processing unit.
 *
 * @see GraphicsProcessingUnit
 */
typealias GPU = GraphicsProcessingUnit

/**
 * Shorthand for power supply unit.
 *
 * @see PowerSupplyUnit
 */
typealias PSU = PowerSupplyUnit

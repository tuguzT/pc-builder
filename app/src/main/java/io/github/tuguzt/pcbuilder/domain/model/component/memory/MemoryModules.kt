package io.github.tuguzt.pcbuilder.domain.model.component.memory

import io.nacular.measured.units.BinarySize
import io.nacular.measured.units.Measure

/**
 * Data class represents module type of memory.
 *
 * @see Memory
 */
data class MemoryModules(val count: UInt, val capacity: Measure<BinarySize>)

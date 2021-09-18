package com.mirea.tuguzt.pcbuilder.domain.model.cpu

import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.Power
import io.nacular.measured.units.Distance
import io.nacular.measured.units.Measure

/**
 * Interface for all central processing units (CPU) of PC.
 *
 * @see CPU
 */
interface CentralProcessingUnit : Component {
    val coreCount: UInt
    val coreClock: Measure<Frequency>
    val thermalDesignPower: Measure<Power>
    val simultaneousMultithreading: Boolean
    val eccType: CpuECCType
    val technologyNode: Measure<Distance>
    // todo
}

/**
 * Shorthand for central processing unit.
 *
 * @see CentralProcessingUnit
 */
typealias CPU = CentralProcessingUnit

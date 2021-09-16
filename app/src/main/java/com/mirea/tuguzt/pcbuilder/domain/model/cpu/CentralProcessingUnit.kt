package com.mirea.tuguzt.pcbuilder.domain.model.cpu

import com.mirea.tuguzt.pcbuilder.domain.GigaHertz
import com.mirea.tuguzt.pcbuilder.domain.Nanometers
import com.mirea.tuguzt.pcbuilder.domain.Watt
import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all central processing units (CPU) of PC.
 */
interface CentralProcessingUnit : Component {
    val coreCount: UInt
    val coreClock: GigaHertz
    val thermalDesignPower: Watt
    val simultaneousMultithreading: Boolean
    val eccType: CpuECCType
    val technologyNode: Nanometers
    // todo
}

package com.mirea.tuguzt.pcbuilder.domain.model.memory

import com.mirea.tuguzt.pcbuilder.domain.MegaHertz
import com.mirea.tuguzt.pcbuilder.domain.Volt
import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all memory (`RAM` stands for `Random Access Memory`) of PC.
 */
interface Memory : Component {
    val formFactor: MemoryFormFactor
    val memoryType: MemoryType
    val voltage: Volt
    val timing: MemoryTiming
    val speed: MegaHertz
    val modules: MemoryModules
    val eccType: MemoryECCType
    val registerType: MemoryRegisterType
    val hasHeatSpreader: Boolean
}

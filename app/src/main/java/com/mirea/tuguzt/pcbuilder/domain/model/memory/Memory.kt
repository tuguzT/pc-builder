package com.mirea.tuguzt.pcbuilder.domain.model.memory

import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.Voltage
import io.nacular.measured.units.Measure

/**
 * Interface for all memory (`RAM` stands for `Random Access Memory`) of PC.
 */
interface Memory : Component {
    val formFactor: MemoryFormFactor
    val memoryType: MemoryType
    val voltage: Measure<Voltage>
    val timing: MemoryTiming
    val speed: Measure<Frequency>
    val modules: MemoryModules
    val eccType: MemoryECCType
    val registerType: MemoryRegisterType
    val hasHeatSpreader: Boolean
}

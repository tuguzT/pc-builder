package io.github.tuguzt.pcbuilder.domain.model.build

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.domain.model.component.cases.Case
import io.github.tuguzt.pcbuilder.domain.model.component.cooler.Cooler
import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CPU
import io.github.tuguzt.pcbuilder.domain.model.component.cpu.CentralProcessingUnit
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GPU
import io.github.tuguzt.pcbuilder.domain.model.component.gpu.GraphicsProcessingUnit
import io.github.tuguzt.pcbuilder.domain.model.component.memory.Memory
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.Monitor
import io.github.tuguzt.pcbuilder.domain.model.component.motherboard.Motherboard
import io.github.tuguzt.pcbuilder.domain.model.component.psu.PSU
import io.github.tuguzt.pcbuilder.domain.model.component.psu.PowerSupplyUnit
import io.github.tuguzt.pcbuilder.domain.model.component.storage.Storage

/**
 * Stores information about custom PC build which consists of [components][Component].
 *
 * @see Component
 */
interface Build {
    val case: Case?
    val cooler: Cooler?
    val centralProcessingUnit: CentralProcessingUnit?
    val graphicsProcessingUnit: GraphicsProcessingUnit?
    val memory: Memory?
    val monitor: Monitor?
    val motherboard: Motherboard?
    val powerSupplyUnit: PowerSupplyUnit?
    val storage: Storage?
}

/**
 * Shorthand for [Build.centralProcessingUnit].
 *
 * @see CPU
 */
val Build.cpu: CPU? get() = centralProcessingUnit

/**
 * Shorthand for [Build.graphicsProcessingUnit].
 *
 * @see GPU
 */
val Build.gpu: GPU? get() = graphicsProcessingUnit

/**
 * Shorthand for [Build.powerSupplyUnit].
 *
 * @see PSU
 */
val Build.psu: PSU? get() = powerSupplyUnit

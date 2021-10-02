package io.github.tuguzt.pcbuilder.domain.model.build

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
 * [Build] which could be mutated.
 *
 * @see Build
 */
interface MutableBuild : Build {
    override var case: Case?
    override var cooler: Cooler?
    override var centralProcessingUnit: CentralProcessingUnit?
    override var graphicsProcessingUnit: GraphicsProcessingUnit?
    override var memory: Memory?
    override var monitor: Monitor?
    override var motherboard: Motherboard?
    override var powerSupplyUnit: PowerSupplyUnit?
    override var storage: Storage?
}

/**
 * Shorthand for [MutableBuild.centralProcessingUnit].
 *
 * @see CPU
 */
inline var MutableBuild.cpu: CPU?
    get() = centralProcessingUnit
    set(value) {
        centralProcessingUnit = value
    }

/**
 * Shorthand for [MutableBuild.graphicsProcessingUnit].
 *
 * @see GPU
 */
inline var MutableBuild.gpu: GPU?
    get() = graphicsProcessingUnit
    set(value) {
        graphicsProcessingUnit = value
    }

/**
 * Shorthand for [MutableBuild.powerSupplyUnit].
 *
 * @see PSU
 */
inline var MutableBuild.psu: PSU?
    get() = powerSupplyUnit
    set(value) {
        powerSupplyUnit = value
    }

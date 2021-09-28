package io.github.tuguzt.pcbuilder.domain.model.component.storage

import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.nacular.measured.units.BinarySize
import io.nacular.measured.units.Measure

/**
 * Interface for all storages of PC.
 */
interface Storage : Component {
    val capacity: Measure<BinarySize>
    val type: StorageType
    val cacheCapacity: Measure<BinarySize>
    val storageInterface: StorageInterface
    val formFactor: StorageFormFactor
}

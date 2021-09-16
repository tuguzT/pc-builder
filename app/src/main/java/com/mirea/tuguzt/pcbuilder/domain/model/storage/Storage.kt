package com.mirea.tuguzt.pcbuilder.domain.model.storage

import com.mirea.tuguzt.pcbuilder.domain.Gigabytes
import com.mirea.tuguzt.pcbuilder.domain.Megabytes
import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all storages of PC.
 */
interface Storage : Component {
    val capacity: Gigabytes
    val type: StorageType
    val cacheCapacity: Megabytes
    val storageInterface: StorageInterface
    val formFactor: StorageFormFactor
}

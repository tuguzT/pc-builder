package com.mirea.tuguzt.pcbuilder.domain.model.memory

import com.mirea.tuguzt.pcbuilder.domain.Gigabytes

/**
 * Data class represents module type of memory.
 *
 * @see Memory
 */
data class MemoryModules(val count: UInt, val capacity: Gigabytes)

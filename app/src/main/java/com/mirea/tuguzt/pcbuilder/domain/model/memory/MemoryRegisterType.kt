package com.mirea.tuguzt.pcbuilder.domain.model.memory

/**
 * Enum represents if the memory have a *register*
 * between the `DRAM` modules and the system's memory controller.
 *
 * @see Memory
 */
enum class MemoryRegisterType {
    Registered,
    Unbuffered,
}

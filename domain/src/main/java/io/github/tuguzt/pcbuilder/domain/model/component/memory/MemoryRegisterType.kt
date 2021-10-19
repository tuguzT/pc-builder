package io.github.tuguzt.pcbuilder.domain.model.component.memory

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

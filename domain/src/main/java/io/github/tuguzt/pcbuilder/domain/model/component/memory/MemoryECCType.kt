package io.github.tuguzt.pcbuilder.domain.model.component.memory

/**
 * Enum represents if the memory chip uses an Error Correction Code (ECC)
 * to detect and correct n-bit data corruption which occurs in memory.
 *
 * @see Memory
 */
enum class MemoryECCType {
    ECC,
    NonECC,
}

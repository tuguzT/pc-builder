package io.github.tuguzt.pcbuilder.domain.model.component.cpu

/**
 * Enum represents if the CPU supports an Error Correction Code (ECC)
 * to detect and correct n-bit data corruption which occurs in memory.
 *
 * @see CentralProcessingUnit
 */
enum class CpuECCType {
    ECC,
    NonECC,
}

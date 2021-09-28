package io.github.tuguzt.pcbuilder.domain.model.component.memory

/**
 * Enum represents all possible types of memory.
 *
 * @see Memory
 */
enum class MemoryType {
    /** Synchronous DRAM (Dynamic RAM) */
    SDRAM,

    /** Rambus DRAM (Dynamic RAM) */
    RDRAM,

    /**
     * Double Data Rate SDRAM
     *
     * @see SDRAM
     */
    DDR,

    /**
     * Second generation of the DDR.
     *
     * @see DDR
     */
    DDR2,

    /**
     * Third generation of the DDR.
     *
     * @see DDR
     */
    DDR3,

    /**
     * Fourth generation of the DDR.
     *
     * @see DDR
     */
    DDR4,

    /**
     * Fifth generation of the DDR.
     *
     * @see DDR
     */
    DDR5,
}

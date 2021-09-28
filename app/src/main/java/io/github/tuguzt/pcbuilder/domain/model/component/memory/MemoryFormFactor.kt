package io.github.tuguzt.pcbuilder.domain.model.component.memory

/**
 * Sealed class represents all form factors types of the memory.
 *
 * @see Memory
 */
sealed class MemoryFormFactor {
    /** Dual In-line Memory Module */
    class DIMM(val pins: Pins) : MemoryFormFactor() {
        /**
         * Enum represents possible count of pins for `DIMM` memory form factor.
         *
         * @see DIMM
         */
        enum class Pins(val count: UInt) {
            PINS_100(100u),
            PINS_168(168u),
            PINS_184(184u),
            PINS_240(240u),
            PINS_288(288u),
        }
    }

    /**
     * Small Outline DIMM (Dual In-line Memory Module)
     *
     * @see DIMM
     */
    class SmallOutlineDIMM(val pins: Pins) : MemoryFormFactor() {
        /**
         * Enum represents possible count of pins for `SO-DIMM` memory form factor.
         *
         * @see SmallOutlineDIMM
         */
        enum class Pins(val count: UInt) {
            PINS_100(100u),
            PINS_144(144u),
            PINS_200(200u),
            PINS_204(204u),
            PINS_260(260u),
        }
    }
}

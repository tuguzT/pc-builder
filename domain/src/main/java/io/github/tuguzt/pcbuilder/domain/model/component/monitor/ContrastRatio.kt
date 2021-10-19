package io.github.tuguzt.pcbuilder.domain.model.component.monitor

/**
 * Data class represents contrast ratio of the monitor.
 *
 * @see Monitor
 */
data class ContrastRatio(val higherLuminance: UInt, val lowerLuminance: UInt) {
    override fun toString() = "$higherLuminance:$lowerLuminance"
}

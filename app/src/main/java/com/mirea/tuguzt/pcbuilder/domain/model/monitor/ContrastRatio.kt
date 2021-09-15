package com.mirea.tuguzt.pcbuilder.domain.model.monitor

/**
 * Data class represents contrast ratio of the monitor.
 */
data class ContrastRatio(val higherLuminance: UInt, val lowerLuminance: UInt) {
    override fun toString() = "$higherLuminance:$lowerLuminance"
}

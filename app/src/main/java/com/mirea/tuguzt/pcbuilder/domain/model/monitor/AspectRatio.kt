package com.mirea.tuguzt.pcbuilder.domain.model.monitor

/**
 * Data class represents aspect ratio of the monitor.
 */
data class AspectRatio(val width: UInt, val height: UInt) {
    override fun toString() = "$width:$height"
}

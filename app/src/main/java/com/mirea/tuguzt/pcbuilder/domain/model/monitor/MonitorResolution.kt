package com.mirea.tuguzt.pcbuilder.domain.model.monitor

import com.mirea.tuguzt.pcbuilder.domain.Pixels

/**
 * Data class represents resolution of the monitor in pixels.
 *
 * @see Monitor
 * @see Pixels
 */
data class MonitorResolution(val width: Pixels, val height: Pixels)

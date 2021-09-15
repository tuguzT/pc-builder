package com.mirea.tuguzt.pcbuilder.domain.model

import com.mirea.tuguzt.pcbuilder.domain.Millimeters

/**
 * Data class that represents physical size of the PC component.
 */
data class Size(
    val length: Millimeters,
    val width: Millimeters,
    val height: Millimeters,
)

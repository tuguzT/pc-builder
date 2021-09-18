package com.mirea.tuguzt.pcbuilder.domain.model.gpu

import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all graphics processing units (GPU) of PC.
 */
interface GraphicsProcessingUnit : Component

/**
 * Shorthand for graphics processing unit.
 *
 * @see GraphicsProcessingUnit
 */
typealias GPU = GraphicsProcessingUnit

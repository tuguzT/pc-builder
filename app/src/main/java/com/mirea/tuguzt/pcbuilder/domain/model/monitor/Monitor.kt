package com.mirea.tuguzt.pcbuilder.domain.model.monitor

import com.mirea.tuguzt.pcbuilder.domain.Hertz
import com.mirea.tuguzt.pcbuilder.domain.Millimeters
import com.mirea.tuguzt.pcbuilder.domain.Milliseconds
import com.mirea.tuguzt.pcbuilder.domain.Nit
import com.mirea.tuguzt.pcbuilder.domain.model.Component

/**
 * Interface for all monitors of PC.
 */
interface Monitor : Component {
    val screenSize: Millimeters
    val resolution: MonitorResolution
    val refreshRate: Hertz
    val responseTime: Milliseconds
    val aspectRatio: AspectRatio
    val panelType: PanelType
    val luminance: Nit
    val screenType: ScreenType
    val contrastRatio: ContrastRatio
    val monitorInterface: MonitorInterface
    val pulseWidthModulation: Hertz
    val frameSyncType: FrameSyncType?
}

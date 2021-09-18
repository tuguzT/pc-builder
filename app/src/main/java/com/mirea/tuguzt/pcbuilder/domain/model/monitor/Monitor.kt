package com.mirea.tuguzt.pcbuilder.domain.model.monitor

import com.mirea.tuguzt.pcbuilder.domain.model.Component
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.Luminance
import io.nacular.measured.units.Distance
import io.nacular.measured.units.Measure
import io.nacular.measured.units.Time

/**
 * Interface for all monitors of PC.
 */
interface Monitor : Component {
    val screenSize: Measure<Distance>
    val resolution: MonitorResolution
    val refreshRate: Measure<Frequency>
    val responseTime: Measure<Time>
    val aspectRatio: AspectRatio
    val panelType: PanelType
    val luminance: Measure<Luminance>
    val screenType: ScreenType
    val contrastRatio: ContrastRatio
    val monitorInterface: MonitorInterface
    val pwmType: MonitorPWMType
    val frameSyncType: FrameSyncType?
}

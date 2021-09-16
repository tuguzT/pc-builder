package com.mirea.tuguzt.pcbuilder.domain.model.monitor

import com.mirea.tuguzt.pcbuilder.domain.Hertz
import com.mirea.tuguzt.pcbuilder.domain.model.monitor.MonitorPWMType.PWM

/**
 * Sealed class represents if the monitor uses PWM or not.
 *
 * @see Monitor
 * @see PWM
 */
sealed class MonitorPWMType {
    /** Pulse Width Modulation (PWM) */
    data class PWM(val frequency: Hertz) : MonitorPWMType() {
        init {
            require(frequency > 0u) { "Frequency of PWM monitor must be greater than 0" }
        }
    }

    /** Monitor was built on Flicker-Free technology */
    object FlickerFree : MonitorPWMType()
}

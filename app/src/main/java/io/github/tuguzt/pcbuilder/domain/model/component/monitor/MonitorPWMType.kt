package io.github.tuguzt.pcbuilder.domain.model.component.monitor

import io.github.tuguzt.pcbuilder.domain.model.component.monitor.MonitorPWMType.PWM
import io.github.tuguzt.pcbuilder.domain.model.units.Frequency
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * Sealed class represents if the monitor uses PWM or not.
 *
 * @see Monitor
 * @see PWM
 */
sealed class MonitorPWMType {
    /** Pulse Width Modulation (PWM) */
    data class PWM(val frequency: Measure<Frequency>) : MonitorPWMType() {
        init {
            require(frequency > 0 * hertz) { "Frequency of PWM monitor must be greater than 0" }
        }
    }

    /** Monitor was built on Flicker-Free technology */
    object FlickerFree : MonitorPWMType()
}

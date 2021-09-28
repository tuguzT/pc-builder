@file:Suppress("unused")

package io.github.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import io.github.tuguzt.pcbuilder.domain.model.component.monitor.MonitorPWMType
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.times

object MonitorPWMTypeConverter {
    @JvmStatic
    @TypeConverter
    fun fromPWMType(value: MonitorPWMType?): Double? = when (value) {
        MonitorPWMType.FlickerFree -> 0.0
        is MonitorPWMType.PWM -> value.frequency `in` hertz
        null -> null
    }

    @JvmStatic
    @TypeConverter
    fun toPWMType(value: Double?): MonitorPWMType? = when (value) {
        0.0 -> MonitorPWMType.FlickerFree
        null -> null
        else -> MonitorPWMType.PWM(value * hertz)
    }
}

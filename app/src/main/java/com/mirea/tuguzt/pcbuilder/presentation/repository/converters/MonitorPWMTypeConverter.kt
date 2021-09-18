@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import com.mirea.tuguzt.pcbuilder.domain.model.monitor.MonitorPWMType
import com.mirea.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.times

object MonitorPWMTypeConverter {
    @TypeConverter
    fun fromPWMType(value: MonitorPWMType?): Double? {
        return when (value) {
            MonitorPWMType.FlickerFree -> 0.0
            is MonitorPWMType.PWM -> value.frequency `in` hertz
            null -> null
        }
    }

    @TypeConverter
    fun toPWMType(value: Double?): MonitorPWMType? {
        return when (value) {
            0.0 -> MonitorPWMType.FlickerFree
            null -> null
            else -> MonitorPWMType.PWM(value * hertz)
        }
    }
}

@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object FrequencyConverter {
    @JvmStatic
    @TypeConverter
    fun fromFrequency(value: Measure<Frequency>?): Double? = value?.let { it `in` hertz }

    @JvmStatic
    @TypeConverter
    fun toFrequency(value: Double?): Measure<Frequency>? = value?.let { it * hertz }
}

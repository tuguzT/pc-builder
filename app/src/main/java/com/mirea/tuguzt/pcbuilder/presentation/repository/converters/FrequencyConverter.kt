@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import com.mirea.tuguzt.pcbuilder.domain.model.units.Frequency
import com.mirea.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object FrequencyConverter {
    @TypeConverter
    fun fromFrequency(value: Measure<Frequency>?): Double? {
        return value?.let { it `in` hertz }
    }

    @TypeConverter
    fun toFrequency(value: Double?): Measure<Frequency>? {
        return value?.let { value * hertz }
    }
}

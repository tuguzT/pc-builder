@file:Suppress("unused")

package io.github.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import io.github.tuguzt.pcbuilder.domain.model.units.Frequency
import io.github.tuguzt.pcbuilder.domain.model.units.hertz
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * Converts [frequency][Frequency] to [hertz] (represented by [Double]) and vice-versa.
 */
object FrequencyConverter {
    @JvmStatic
    @TypeConverter
    fun fromFrequency(value: Measure<Frequency>?): Double? = value?.let { it `in` hertz }

    @JvmStatic
    @TypeConverter
    fun toFrequency(value: Double?): Measure<Frequency>? = value?.let { it * hertz }
}

@file:Suppress("unused")

package io.github.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Measure
import io.nacular.measured.units.Time
import io.nacular.measured.units.Time.Companion.milliseconds
import io.nacular.measured.units.times

/**
 * Converts [time][Time] to [milliseconds] (represented by [Double]) and vice-versa.
 */
object TimeConverter {
    @JvmStatic
    @TypeConverter
    fun fromTime(value: Measure<Time>?): Double? = value?.let { it `in` milliseconds }

    @JvmStatic
    @TypeConverter
    fun toTime(value: Double?): Measure<Time>? = value?.let { it * milliseconds }
}

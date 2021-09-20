@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Measure
import io.nacular.measured.units.Time
import io.nacular.measured.units.Time.Companion.milliseconds
import io.nacular.measured.units.times

object TimeConverter {
    @JvmStatic
    @TypeConverter
    fun fromTime(value: Measure<Time>?): Double? = value?.let { it `in` milliseconds }

    @JvmStatic
    @TypeConverter
    fun toTime(value: Double?): Measure<Time>? = value?.let { it * milliseconds }
}

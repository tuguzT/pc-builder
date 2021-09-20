@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import com.mirea.tuguzt.pcbuilder.domain.model.units.Luminance
import com.mirea.tuguzt.pcbuilder.domain.model.units.nit
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object LuminanceConverter {
    @JvmStatic
    @TypeConverter
    fun fromLuminance(value: Measure<Luminance>?): Double? = value?.let { it `in` nit }

    @JvmStatic
    @TypeConverter
    fun toLuminance(value: Double?): Measure<Luminance>? = value?.let { it * nit }
}

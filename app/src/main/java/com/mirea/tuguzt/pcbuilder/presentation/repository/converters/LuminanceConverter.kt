@file:Suppress("unused")

package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import com.mirea.tuguzt.pcbuilder.domain.model.units.Luminance
import com.mirea.tuguzt.pcbuilder.domain.model.units.nit
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object LuminanceConverter {
    @TypeConverter
    fun fromLuminance(value: Measure<Luminance>?): Double? {
        return value?.let { it `in` nit }
    }

    @TypeConverter
    fun toLuminance(value: Double?): Measure<Luminance>? {
        return value?.let { value * nit }
    }
}

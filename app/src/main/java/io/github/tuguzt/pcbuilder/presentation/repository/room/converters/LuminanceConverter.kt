@file:Suppress("unused")

package io.github.tuguzt.pcbuilder.presentation.repository.room.converters

import androidx.room.TypeConverter
import io.github.tuguzt.pcbuilder.domain.model.units.Luminance
import io.github.tuguzt.pcbuilder.domain.model.units.nit
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * Converts [luminance][Luminance] to [nit] (represented by [Double]) and vice-versa.
 */
object LuminanceConverter {
    @JvmStatic
    @TypeConverter
    fun fromLuminance(value: Measure<Luminance>?): Double? = value?.let { it `in` nit }

    @JvmStatic
    @TypeConverter
    fun toLuminance(value: Double?): Measure<Luminance>? = value?.let { it * nit }
}

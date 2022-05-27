package io.github.tuguzt.pcbuilder.repository.room.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

/**
 * Converts [mass][Mass] to [grams] (represented by [Double]) and vice-versa.
 */
object MassConverter {
    @JvmStatic
    @TypeConverter
    fun fromMass(value: Measure<Mass>?): Double? = value?.let { it `in` grams }

    @JvmStatic
    @TypeConverter
    fun toMass(value: Double?): Measure<Mass>? = value?.let { it * grams }
}

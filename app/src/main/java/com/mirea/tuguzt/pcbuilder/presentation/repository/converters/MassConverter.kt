package com.mirea.tuguzt.pcbuilder.presentation.repository.converters

import androidx.room.TypeConverter
import io.nacular.measured.units.Mass
import io.nacular.measured.units.Mass.Companion.grams
import io.nacular.measured.units.Measure
import io.nacular.measured.units.times

object MassConverter {
    @JvmStatic
    @TypeConverter
    fun fromMass(value: Measure<Mass>?): Double? = value?.let { it `in` grams }

    @JvmStatic
    @TypeConverter
    fun toMass(value: Double?): Measure<Mass>? = value?.let { it * grams }
}
